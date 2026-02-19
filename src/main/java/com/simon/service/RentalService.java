package com.simon.service;

import com.simon.entity.*;
import com.simon.repo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class RentalService {

    private RentalRepo rentalRepo;
    private MemberRepo memberRepo;
    private BikeRepo bikeRepo;
    private KayakRepo kayakRepo;
    private TentRepo tentRepo;
    private InventoryService inventoryService;
    private IncomeService incomeService;

    public RentalService() {
    }

    public RentalService(RentalRepo rentalRepo, MemberRepo memberRepo, BikeRepo bikeRepo,  KayakRepo kayakRepo, TentRepo tentRepo, InventoryService inventoryService, IncomeService incomeService) {
        this.rentalRepo       = rentalRepo;
        this.memberRepo       = memberRepo;
        this.bikeRepo         = bikeRepo;
        this.kayakRepo        = kayakRepo;
        this.tentRepo         = tentRepo;
        this.inventoryService = inventoryService;
        this.incomeService    = incomeService;
    }

    public Object getItem( RentalType rentalType, Long rentalObjectId ){
        
        return switch ( rentalType ) {
            case BIKE -> bikeRepo.findById( rentalObjectId ).orElse( null );
            case KAYAK -> kayakRepo.findById( rentalObjectId ).orElse( null );
            case TENT -> tentRepo.findById( rentalObjectId ).orElse( null );
        };
    }

    public List<Rental> getAllRentals() {
        return rentalRepo.findAll();
    }

    public boolean isItemAvailable( RentalType rentalType, Long rentalObjectId ){

        Object item = getItem( rentalType, rentalObjectId );

        if( item instanceof Bike b ) {
            return b.getStatus() == ItemStatus.AVAILABLE;
        }

        else if( item instanceof Kayak k ) {
            return k.getStatus() == ItemStatus.AVAILABLE;
        }

        else if( item instanceof Tent t ) {
            return t.getStatus() == ItemStatus.AVAILABLE;
        }

        return false;
    }

    public void updateItemStatus( RentalType rentalType, Long rentalObjectId, ItemStatus status ) {

        Object item = getItem( rentalType, rentalObjectId );

        if( item instanceof Bike b ) {
            b.setStatus( status );
            bikeRepo.update( b );
        }

        else if( item instanceof Kayak k ) {
            k.setStatus( status );
            kayakRepo.update( k );
        }

        else if( item instanceof Tent t ) {
            t.setStatus( status );
            tentRepo.update( t );
        }
    }

    public void updateRental(Rental rental) {
        rentalRepo.update(rental);

        if (rental.getReturnDate() != null) {
            Long itemId = rental.getRentalObjectId();

            if (rental.getRentalType() == RentalType.BIKE) {

                Bike b = inventoryService.findById(Bike.class, itemId);
                if (b != null) {
                    b.setStatus(ItemStatus.AVAILABLE);
                    inventoryService.update(b);
                }
            } else if (rental.getRentalType() == RentalType.KAYAK) {
                Kayak k = inventoryService.findById(Kayak.class, itemId);
                if (k != null) {
                    k.setStatus(ItemStatus.AVAILABLE);
                    inventoryService.update(k);
                }
            } else if (rental.getRentalType() == RentalType.TENT) {
                Tent t = inventoryService.findById(Tent.class, itemId);
                if (t != null) {
                    t.setStatus(ItemStatus.AVAILABLE);
                    inventoryService.update(t);
                }
            }
        }
    }

    public void processNewRental( Rental rental ) {

        updateItemStatus( rental.getRentalType(), rental.getRentalObjectId(), ItemStatus.UNAVAILABLE );
        rentalRepo.save( rental );
    }

    public void processReturn( Rental rental ) {


        updateItemStatus( rental.getRentalType(), rental.getRentalObjectId(), ItemStatus.AVAILABLE );
        rental.setReturnDate( LocalDateTime.now() );
        rentalRepo.update( rental );

        rental.setReturnDate( LocalDateTime.now() );

        BigDecimal pricePerDay = BigDecimal.ZERO;


        if( rental.getRentalType() == RentalType.BIKE ) {
            Bike bike   = (Bike) getItem( RentalType.BIKE, rental.getRentalObjectId() );
            pricePerDay = bike.getPrice();
        }

        else if( rental.getRentalType() == RentalType.KAYAK ) {
            Kayak kayak = (Kayak) getItem( RentalType.KAYAK, rental.getRentalObjectId() );
            pricePerDay = kayak.getPrice();
        }

        else if( rental.getRentalType() == RentalType.TENT ) {
            Tent tent   = (Tent) getItem( RentalType.TENT, rental.getRentalObjectId() );
            pricePerDay = tent.getPrice();
        }

        Duration duration       = Duration.between( rental.getRentalDate(), rental.getReturnDate() );
        double daysWithDecimals = duration.toSeconds() / 86400.0;
        long billableDays = (long) Math.ceil( daysWithDecimals );

        IO.println( pricePerDay.doubleValue() + " " +  billableDays );
        IO.println( billableDays );

        double amount = pricePerDay.doubleValue() *  billableDays;

        incomeService.addIncome( new Income( BigDecimal.valueOf( amount ), LocalDateTime.now(), rental ) );
    }
}
