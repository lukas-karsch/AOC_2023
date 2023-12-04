import {readFileSync} from "fs";

function getInput() {
    return readFileSync("./01/input.txt", "utf-8");
}

function getFirstDigit(line) {
    for(let i=0; i<line.length; i++) {
        const char = line.charAt(i);
        if(Number.isInteger(parseInt(char))) {
            //console.log(char, "is integer");
            return char;
        }
    }
    return null;
}

function getLastDigit(line) {
    for(let i=line.length; i>=0; i--) {
        const char = line.charAt(i);
        if(Number.isInteger(parseInt(char))) {
            //console.log(char, "is integer");
            return char;
        }
    }
    return null;
}

function getLineDigits(line) {
    let result = "";
    result += getFirstDigit(line);
    result += getLastDigit(line);
    console.log(result);
    if(result.length !== 2) {
        return 0;
    }
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
