package com.orm.lab02.repository;

import com.orm.lab02.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c JOIN CustomerOrder o ON c.clientId = o.client.clientId GROUP BY c ORDER BY COUNT(o) DESC")
    List<Client> getClientsWithMostOrders();
}
