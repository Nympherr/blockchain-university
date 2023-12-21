// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

contract Lottery {

    address public owner;
    constructor() {
        owner = msg.sender; // Assign contract deployer as the owner
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "Only contract owner can perform this action");
        _;
    }

    uint256 totalWinnings = 0;
    uint public winningOption = 0;

    mapping(uint256 => address[]) public betsByOption;
    mapping(address => bool) public hasPlacedBet;
    address[] public addressesWithBets;

    function resetHasPlacedBet() public {
        for (uint i = 0; i < addressesWithBets.length; i++) {
            hasPlacedBet[addressesWithBets[i]] = false;
        }
    }

    modifier maxEther(uint256 amount) {
        require(amount == 0.05 ether, "Exceeded maximum amount");
        _;
    }

    modifier onlyOnce() {
        require(!hasPlacedBet[msg.sender], "Address has already placed a bet");
        _;
    }

    function placeBet(uint256 option) public payable maxEther(msg.value) onlyOnce {
        require(option >= 1 && option <= 3, "Invalid option");

        betsByOption[option].push(msg.sender);
        totalWinnings += msg.value;

        hasPlacedBet[msg.sender] = true; // Mark the address as having placed a bet

        bool exists;
        for (uint i = 0; i < addressesWithBets.length; i++) {
            if (addressesWithBets[i] == msg.sender) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            addressesWithBets.push(msg.sender); // Add the address to the array if it's not already there
        }
    }

    function setRandomNumber() external onlyOwner  {
        winningOption = uint (keccak256(abi.encodePacked (msg.sender, block.timestamp, winningOption)));
        winningOption = (winningOption % 3) + 1;
    }


    function distributeWinnings() external onlyOwner {
        require(totalWinnings > 0, "No winnings to distribute");
        require(betsByOption[winningOption].length > 0, "No participants for winning option");

        uint256 totalParticipants = betsByOption[winningOption].length;
        uint256 winningsPerParticipant = totalWinnings / totalParticipants;

        for (uint256 i = 0; i < totalParticipants; i++) {
            address payable participant = payable(betsByOption[winningOption][i]);
            participant.transfer(winningsPerParticipant);
        }

        totalWinnings = 0;
        winningOption = 0;

        for (uint256 j = 1; j <= 3; j++) {
            delete betsByOption[j];
        }
        resetHasPlacedBet();
    }
}
