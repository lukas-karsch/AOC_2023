import {readFileSync} from "fs";

function getInput() {
    return readFileSync("./01/input.txt", "utf-8");
}

function getFirstDigit(line) {
    for(let i=0; i<line.length; i++) {
        const char = line.charAt(i);
        if(Number.isInteger(parseInt(char))) {
            return {index: i, char: char};
        }
    }
    return {index: -1}
}

/**
 * @param {string} line 
 */
function getFirstNamedDigit(line) {
    for (let i = 0; i < line.length - 3; i++) {
        const sub = line.substring(i, Math.min(i+5, line.length));
        const NamedDigit = isNamedDigit(sub);
        if(NamedDigit) {
            return {index: i, digit: NamedDigit};
        }
    }
    return {index: -1}
}

/**
 * @param {string} line 
 */
function getLastNamedDigit(line) {
    for (let i = line.length - 3; i > 0; i--) {
        const sub = line.substring(i, Math.min(i+5, line.length));
        const NamedDigit = isNamedDigit(sub);
        if(NamedDigit) {
            return {index: i, digit: NamedDigit};
        }
    }
    return {index: -1}
}

function getLastDigit(line) {
    for(let i=line.length; i>=0; i--) {
        const char = line.charAt(i);
        if(Number.isInteger(parseInt(char))) {
            return {index: i, char: char};
        }
    }
    return {index: -1}
}

/**
 * @param {string} input 
 */
function isNamedDigit(input) {
    if(input.startsWith("one")) return 1;
    if(input.startsWith("two")) return 2;
    if(input.startsWith("three")) return 3;
    if(input.startsWith("four")) return 4;
    if(input.startsWith("five")) return 5;
    if(input.startsWith("six")) return 6;
    if(input.startsWith("seven")) return 7;
    if(input.startsWith("eight")) return 8;
    if(input.startsWith("nine")) return 9;
}

function getLineDigits(line) {
    let result = "";
    let first = "";
    let second = "";
    const firstNamedDigit = getFirstNamedDigit(line);
    const firstDigit = getFirstDigit(line);
    if(firstNamedDigit.index === -1) first += firstDigit.char;
    else {
        if(firstDigit.index < firstNamedDigit.index) first += firstDigit.char;
        else first += firstNamedDigit.digit;
    }
    const lastNamedDigit = getLastNamedDigit(line);
    const lastDigit = getLastDigit(line);
    if(lastNamedDigit.index === -1) second += lastDigit.char;
    else {
        if(lastDigit.index > lastNamedDigit.index) second += lastDigit.char;
        else second += lastNamedDigit.digit;
    }
    result = first + second;
    console.log(line, result);
    return result;
}

function sumAll(input) {
    const split = input.split("\n");
    console.log("Split into", split.length, "lines");
    let result = 0;
    for(const line of split) {
        result += parseInt(getLineDigits(line));
    }
    return result;
} 

function main() {
    console.log(sumAll(getInput()));
}

main();
