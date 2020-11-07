package com.albertorsini.mpp.engine.service;

import com.albertorsini.mpp.engine.exception.BankStatementProcessingException;
import com.albertorsini.mpp.model.*;
import com.albertorsini.mpp.repository.BankStatementRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class BankStatementService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BankStatementService.class);

  private final EventService eventService;
  private final BankStatementRepository bankStatementRepository;

  public BankStatementService(
    final EventService eventService,
    final BankStatementRepository bankStatementRepository
  ) {
    this.eventService = eventService;
    this.bankStatementRepository = bankStatementRepository;
  }

  public BankStatement create(final BankStatementRequest bankStatementRequest, final String walletId) {

    requireNonNull(bankStatementRequest, "bankStatementRequest must not be null");
    requireNonNull(walletId, "walletId must not be null");

    try (final FileInputStream excelFile = new FileInputStream(new File(bankStatementRequest.getPath()));
         final Workbook workbook = new XSSFWorkbook(excelFile)) {

      LOGGER.info("start elaborating file {}", bankStatementRequest.getPath());

      workbook.forEach(sheet ->
        {
          final Iterator<Row> iterator = sheet.rowIterator();
          iterator.next();

          IteratorUtils.toList(iterator)
            .stream()
            .filter(row -> row.getCell(0).getDateCellValue() != null)
            .forEach(row -> createEventAndUpdateTotals(walletId, row));
        }
      );

      LOGGER.info("end elaborating file {}", bankStatementRequest.getPath());

      final BankStatement bankStatement = new BankStatement();
      bankStatement.setId(UUID.randomUUID().toString());
      bankStatement.setPath(bankStatementRequest.getPath());
      bankStatement.setWalletId(walletId);

      return bankStatementRepository.save(bankStatement);

    } catch (final Exception e) {
      throw new BankStatementProcessingException(e, bankStatementRequest.getPath());
    }
  }

  @Transactional
  public void deleteByWalletId(final String walletId) {

    requireNonNull(walletId, "walletId must not be null");

    bankStatementRepository.deleteByWalletId(walletId);
  }

  private void createEventAndUpdateTotals(final String walletId, final Row row) {

    final EventRequest eventRequest = new EventRequest();

    eventRequest.setAccountingDate(row.getCell(0).getDateCellValue());
    eventRequest.setValueDate(row.getCell(1).getDateCellValue());
    final double amount = row.getCell(2).getNumericCellValue();
    eventRequest.setAmount(amount);
    eventRequest.setCurrency(Currency.valueOf(row.getCell(3).getStringCellValue()));
    eventRequest.setDescription(row.getCell(4).getStringCellValue());

    float signum = Math.signum((float) amount);
    final EventType type;

    if (signum == 1) {
      type = EventType.INCOME;
    } else if (signum == -1) {
      type = EventType.EXPENSE;
    } else {
      type = EventType.EXPENSE;
    }

    eventRequest.setType(type);

    eventService.create(walletId, eventRequest);
  }
}
