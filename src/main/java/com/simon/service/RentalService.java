package com.simon.service;

import com.simon.entity.*;
import com.simon.repo.*;

public class RentalService {

    private RentalRepo rentalRepo;
    private MemberRepo memberRepo;
    private BikeRepo bikeRepo;
    private KayakRepo kayakRepo;
    private TentRepo tentRepo;

    public RentalService() {
    }

    public RentalService(RentalRepo rentalRepo, MemberRepo memberRepo, BikeRepo bikeRepo,  KayakRepo kayakRepo, TentRepo tentRepo) {
        this.rentalRepo = rentalRepo;
        this.memberRepo = memberRepo;
        this.bikeRepo   = bikeRepo;
        this.kayakRepo  = kayakRepo;
        this.tentRepo   = tentRepo;
    }

    public Object getItem( RentalType rentalType, Long rentalObjectId ){
        
        return switch ( rentalType ) {
            case BIKE -> bikeRepo.findById( rentalObjectId ).orElse( null );
            case KAYAK -> kayakRepo.findById( rentalObjectId ).orElse( null );
            case TENT -> tentRepo.findById( rentalObjectId ).orElse( null );
        };
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

    public void processNewRental( Rental rental ) {

        //if( !isItemAvailable( rental.getRentalType(), rental.getRentalObjectId() ) )
            //throw new RuntimeException( rental.getRentalType() + " is not available" );

        updateItemStatus( rental.getRentalType(), rental.getRentalObjectId(), ItemStatus.UNAVAILABLE );
        rentalRepo.save( rental );
    }

    public void processReturn( Rental rental ) {

        updateItemStatus( rental.getRentalType(), rental.getRentalObjectId(), ItemStatus.AVAILABLE );

        rentalRepo.delete( rental );
    }
}
