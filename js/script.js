
let activePlay = false;
let selectedAccount = null;
let seconds = 60;
let numberOfPlayers = 0;
const web3 = new Web3("http://127.0.0.1:7545");
const contractAddress = '0x1eD794b098B209579f11360Bc493ECCf30a228cC';
const contractABI = [
    {
    "inputs": [],
    "stateMutability": "nonpayable",
    "type": "constructor"
},
{
    "inputs": [
    {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
    }
    ],
    "name": "addressesWithBets",
    "outputs": [
    {
        "internalType": "address",
        "name": "",
        "type": "address"
    }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
},
{
    "inputs": [
    {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
    },
    {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
    }
    ],
    "name": "betsByOption",
    "outputs": [
    {
        "internalType": "address",
        "name": "",
        "type": "address"
    }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
},
{
    "inputs": [
    {
        "internalType": "address",
        "name": "",
        "type": "address"
    }
    ],
    "name": "hasPlacedBet",
    "outputs": [
    {
        "internalType": "bool",
        "name": "",
        "type": "bool"
    }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
},
{
    "inputs": [],
    "name": "owner",
    "outputs": [
    {
        "internalType": "address",
        "name": "",
        "type": "address"
    }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
},
{
    "inputs": [],
    "name": "winningOption",
    "outputs": [
    {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
    }
    ],
    "stateMutability": "view",
    "type": "function",
    "constant": true
},
{
    "inputs": [],
    "name": "resetHasPlacedBet",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
},
{
    "inputs": [
    {
        "internalType": "uint256",
        "name": "option",
        "type": "uint256"
    }
    ],
    "name": "placeBet",
    "outputs": [],
    "stateMutability": "payable",
    "type": "function",
    "payable": true
},
{
    "inputs": [],
    "name": "setRandomNumber",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
},
{
    "inputs": [],
    "name": "distributeWinnings",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
    }];
const contract = new web3.eth.Contract(contractABI, contractAddress);

const showPlayers = document.getElementById('show-players');
const choiceBtns = document.querySelectorAll('.choice-btn');
const playerInfo = document.getElementById('players');
const invokeBtn = document.getElementById('interact-smart-contract');
const showPrize = document.getElementById('show-prize');
invokeBtn.addEventListener('click', callSmartContractFunction);
document.querySelector('.choices-div').addEventListener('click', handleChoiceButtonClick);
document.getElementById('interact-smart-contract').addEventListener('click', handleInteractionButtonClick);

async function callSmartContractFunction() {

    if (choiceOpt !== null) {
        console.log("Selected choice:", choiceOpt);
        this.classList.add('active-interaction');
        activePlay = true;

        try {
            const result = await contract.methods.placeBet(choiceOpt).send({
                from: selectedAccount,
                value: web3.utils.toWei('0.05', 'ether'), 
                gas: 6000000
            });
            console.log("Smart contract function invoked:", result);
            getEtherBalance();
            getPlayerInfo();
        }
        catch (error) {
            if (error.message.includes("Address has already placed a bet")) {
                window.alert("Šita paskyra jau žaidžia!");
            } else {
                window.alert("Įvyko klaida");
            }
            console.error("Įvyko klaida", error);
        }
    } 
    else {
        window.alert("Turite išsirinkti pasirinkimą");
    }
  }

  function startTimer() {
    getEtherBalance();
    getPlayerInfo();
    const timerElement = document.getElementById('timer');
    const countdown = setInterval(() => {
        timerElement.textContent = seconds;
        seconds--;

        if (seconds < 0) {
            clearInterval(countdown);
            seconds = 60;
            callFirstSmartContractFunction();
            callSecondSmartContractFunction();
            getEtherBalance();
            numberOfPlayers = 0;
            startTimer();
        }
    }, 1000);
    getEtherBalance();
    getPlayerInfo();
}

async function callFirstSmartContractFunction() {
    try {
        const result = await contract.methods.setRandomNumber().send({
            from: '0xC362394Ca1a0Cf6f7Df4becb449F8ae604a3db59',
        });
    } catch (error) {
      console.error("Error invoking first smart contract function:", error);
    }
  }
  
  async function callSecondSmartContractFunction() {
    try {
        const result = await contract.methods.distributeWinnings().send({
            from: '0xC362394Ca1a0Cf6f7Df4becb449F8ae604a3db59',
            gas: 6000000
        });
        numberOfPlayers = 0;
    } catch (error) {
      console.error("Error invoking second smart contract function:", error);
    }
  }

choiceBtns.forEach((btn) => {
    btn.addEventListener('click', function() {
      if (!activePlay) {
        choiceBtns.forEach((button) => {
          button.classList.remove('active');
        });
  
        this.classList.add('active');
      }
    });
  });

let choiceOpt = null;

function handleChoiceButtonClick(event) {
  if (!activePlay && event.target.classList.contains('choice-btn')) {

    if (event.target.id === 'first-choice') {
      choiceOpt = 1;
    } else if (event.target.id === 'second-choice') {
      choiceOpt = 2;
    } else if (event.target.id === 'third-choice') {
      choiceOpt = 3;
    }
  }
}

function getEtherBalance(){
    web3.eth.getBalance(contractAddress)
    .then(balance => {
        const balanceInEther = web3.utils.fromWei(balance, 'ether');
        showPrize.textContent = 'Dabartinis prizinis fondas: ' + balanceInEther + ' ETHER';
        console.log(`Balance of ${contractAddress}: ${balanceInEther} ETH`);
    })
    .catch(err => {
        console.error('Error fetching balance:', err);
});
}
function getPlayerInfo(){
    showPlayers.textContent = 'Dabartinis dalyvių skaičius: ' + numberOfPlayers;
}

function handleInteractionButtonClick() {
    if (choiceOpt !== null) {
        this.classList.add('active-interaction');
        activePlay = true;
        numberOfPlayers++;
        getPlayerInfo();
        getEtherBalance();
        window.alert("Klaida!");
    }
    else {
    window.alert("Taip negalima");
    }
}

async function populateDropdown() {

    const accountsDropdown = document.getElementById('ethereum-accounts');
    try {
        const web3 = new Web3("http://127.0.0.1:7545");
        const accounts = await web3.eth.getAccounts();
    
        accounts.forEach((account, index) => {
        const option = document.createElement('option');
        option.value = account;
        option.text = `Paskyra ${index + 1}: ${account}`;
        accountsDropdown.appendChild(option);
        });
    
        accountsDropdown.addEventListener('change', handleAccountChange);
    } 
    catch (error) {
        console.error('Error fetching accounts:', error);
    }
}
  
function handleAccountChange(event) {
    selectedAccount = event.target.value;
}

function handleAccountChange(event) {
    selectedAccount = event.target.value;
    activePlay = false;
    invokeBtn.classList.remove('active-interaction');
}

startTimer();
populateDropdown();
getEtherBalance();
getPlayerInfo();