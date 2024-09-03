package com.example.demo.service;

import com.example.demo.model.UPITransaction; // Assuming you have this class
import com.example.demo.model.BankTransferTransaction; // Assuming you have this class
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private UPITransaction upiTransaction;
    private BankTransferTransaction bankTransferTransaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        upiTransaction = new UPITransaction( 1000.0, LocalDate.now(), "Success", "upiId1", "userId1");
        bankTransferTransaction = new BankTransferTransaction("Bank Transfer",2000.0, LocalDate.now().minusDays(1), "Pending","3", "accountNumber1","receiever1");
    }

    @Test
    public void testAddTransactionService() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(upiTransaction);

        Transaction savedTransaction = transactionService.addTransactionService(upiTransaction);

        assertNotNull(savedTransaction);
        assertEquals(0, savedTransaction.getTransactionId());
        assertEquals(1000.0, savedTransaction.getAmount());
        verify(transactionRepository, times(1)).save(upiTransaction);
    }

    @Test
    public void testTrackTransactionStatus() throws Exception {
        when(transactionRepository.findByStatus(1)).thenReturn("Success");

        String status = transactionService.trackTransactionStatus(1);

        assertEquals("Success", status);
        verify(transactionRepository, times(1)).findByStatus(1);
    }

    @Test
    public void testGetTransactionHistory() {
        // Add multiple transactions (including subclasses) to the mocked repository
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(upiTransaction, bankTransferTransaction));

        List<Transaction> transactions = transactionService.getTransactionHistory();

        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(upiTransaction));
        assertTrue(transactions.contains(bankTransferTransaction));
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    public void testGetTransactionById() {
        when(transactionRepository.findByTransactionId(1)).thenReturn(Optional.of(upiTransaction));

        Optional<Transaction> foundTransaction = transactionService.getTransactionById(1);

        assertTrue(foundTransaction.isPresent());
        assertEquals(upiTransaction, foundTransaction.get());
        verify(transactionRepository, times(1)).findByTransactionId(1);
    }

    @Test
    public void testGetTransactionById_NotFound() {
        when(transactionRepository.findByTransactionId(2)).thenReturn(Optional.empty());

        Optional<Transaction> foundTransaction = transactionService.getTransactionById(2);

        assertFalse(foundTransaction.isPresent());
        verify(transactionRepository, times(1)).findByTransactionId(2);
    }
}
