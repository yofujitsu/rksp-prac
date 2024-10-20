// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract RentalContract {
    address public landlord;
    address public tenant;
    uint256 public monthlyRent;
    uint256 public securityDeposit;
    uint256 public rentDueDate;

    bool public isRented;

    event RentPaid(address indexed tenant, uint256 amount);
    event DepositReturned(address indexed tenant, uint256 amount);
    event ContractCreated(address indexed landlord, address indexed tenant, uint256 monthlyRent, uint256 securityDeposit);

    constructor(address _tenant, uint256 _monthlyRent, uint256 _deposit) {
        landlord = msg.sender;
        tenant = _tenant;
        monthlyRent = _monthlyRent;
        securityDeposit = _deposit;
        rentDueDate = block.timestamp + 30 days;
        isRented = true;

        emit ContractCreated(landlord, tenant, monthlyRent, securityDeposit);
    }

    modifier onlyTenant() {
        require(msg.sender == tenant, "Only the tenant can perform this action");
        _;
    }

    modifier onlyLandlord() {
        require(msg.sender == landlord, "Only the landlord can perform this action");
        _;
    }

    modifier rentalActive() {
        require(isRented, "The property is no longer rented");
        _;
    }

    function payRent() public payable onlyTenant rentalActive {
        require(msg.value == monthlyRent, "Incorrect rent amount");
        require(block.timestamp <= rentDueDate, "Rent is overdue");

        (bool success, ) = landlord.call{value: msg.value}("");
        require(success, "Transfer failed");

        rentDueDate += 30 days;

        emit RentPaid(msg.sender, msg.value);
    }

    function returnDeposit() public onlyLandlord {
        require(block.timestamp > rentDueDate, "Rent period still active");
        require(!isRented, "Deposit already returned");

        isRented = false;

        (bool success, ) = tenant.call{value: securityDeposit}("");
        require(success, "Transfer failed");

        emit DepositReturned(tenant, securityDeposit);
    }

    function setRentalPeriodEnd(uint256 _endTime) public {
       require(msg.sender == landlord, "Only landlord can set rental period end");
       rentDueDate = _endTime;
   }
}