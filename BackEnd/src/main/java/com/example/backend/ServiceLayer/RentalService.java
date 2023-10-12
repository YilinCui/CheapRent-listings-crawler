package com.example.backend.ServiceLayer;

import com.example.backend.Entity.Rental;
import com.example.backend.PersistanceLayer.RentalRepository;
import com.example.backend.ApplicationLayer.WebCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    private static final int MAX_T = 8;
    private ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
    private ArrayList<WebCrawler> tasks = new ArrayList<>();

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
    public List<Rental> getAllRentalsAsc() {
        return rentalRepository.findAllByOrderByPriceAsc();
    }
    public List<Rental> getAllRentalsDesc() {
        return rentalRepository.findAllByOrderByPriceDesc();
    }
    public List<Rental> getStudioDesc() {
        return rentalRepository.findStudioDesc();
    }

    public Rental createOrUpdateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

    public Rental getCheapestRental() {
        Pageable topOne = PageRequest.of(0, 1);
        List<Rental> rentals = rentalRepository.findAllByOrderByPriceAsc(topOne);
        if (rentals.isEmpty()) {
            return null;
        }
        return rentals.get(0);
    }

    public void insertRental(String link, String suite, int price, String location) {
        Rental rental = new Rental();
        rental.setLink(link);
        rental.setSuite(suite);
        rental.setPrice(price);
        rental.setLocation(location);

        rentalRepository.save(rental);
        System.out.println("Insert success");
    }

    public void startCrawling(String baseURL) {
        String area;
        int pageNum;

        pageNum = 10;
        area = "irvine-ca";
        createTasks(baseURL + area, area, pageNum);

        for (Runnable task : tasks)
            pool.execute(task);

        pool.shutdown();

        try {
            // 等待线程池在5秒内关闭
            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                // 超过5秒，强制关闭线程池
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // 如果等待过程中发生中断，也强制关闭线程池
            pool.shutdownNow();
            Thread.currentThread().interrupt(); // 保留中断状态
        }
    }

    private void createTasks(String url, String area, int pageNum) {
        url = url + "/";
        for (int i = 1; i <= pageNum; i++) {
            WebCrawler r = new WebCrawler(url + i, i, area, this);
            tasks.add(r);
        }
    }


}
