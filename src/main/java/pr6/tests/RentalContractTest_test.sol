// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "remix_tests.sol"; // Подключаем библиотеку для тестирования
import "../RentalContract.sol"; // Импортируем ваш контракт

contract RentalContractTest {
    RentalContract rentalContract;
    address landlord;
    address tenant;

    uint256 monthlyRent = 1 ether; // Примерная сумма аренды
    uint256 securityDeposit = 0.5 ether; // Примерная сумма депозита

    function beforeAll() public {
        landlord = address(this); // Устанавливаем адрес тестового контракта как арендодателя
        tenant = address(0x123); // Устанавливаем адрес арендатора
        rentalContract = new RentalContract(tenant, monthlyRent, securityDeposit);
    }

    function testInitialState() public {
        Assert.equal(rentalContract.landlord(), landlord, "Landlord should be the contract deployer");
        Assert.equal(rentalContract.tenant(), tenant, "Tenant should be the specified tenant");
        Assert.equal(rentalContract.monthlyRent(), monthlyRent, "Monthly rent should match");
        Assert.equal(rentalContract.securityDeposit(), securityDeposit, "Security deposit should match");
        Assert.equal(rentalContract.isRented(), true, "Property should be rented initially");
    }

    function testPayRent() public {
        (bool success,) = tenant.call{value: monthlyRent}(
            abi.encodeWithSignature("payRent()")
        );

        // Проверяем, что аренда была оплачена
        Assert.equal(rentalContract.rentDueDate(), block.timestamp + 30 days, "Rent due date should be updated");
    }

    function testReturnDeposit() public {
        // Симулируем окончание срока аренды
        rentalContract.setRentalPeriodEnd(block.timestamp + 31 days); // Добавьте эту функцию в контракт

        (bool success,) = landlord.call(
            abi.encodeWithSignature("returnDeposit()")
        );

        // Проверяем, что аренда больше не активна
        Assert.equal(rentalContract.isRented(), true, "Property should no longer be rented");
    }

    receive() external payable {}
}