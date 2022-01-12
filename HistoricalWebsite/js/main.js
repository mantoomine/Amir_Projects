const buttonRun = document.getElementById('test2');
buttonRun.addEventListener('click', () => {
  const randColor = rand();
  document.getElementById('arrPrint').innerHTML = randColor;
  const upper = toUpper(randColor);
  document.getElementById('arrPrint2').innerHTML = upper;
  const lower = toLower(randColor);
  document.getElementById('arrPrint3').innerHTML = lower;
  const count = counts(randColor);
  document.getElementById('arrPrint4').innerHTML = count;
  const consonant = consonants(randColor);
  const count2 = counts(consonant);
  document.getElementById('arrPrint5').innerHTML = count2;
  const vowel = vowels(randColor);
  const count3 = counts(vowel);
  document.getElementById('arrPrint6').innerHTML = count3;
});

const btn2 = document.getElementById('Test3');
btn2.addEventListener('click', () => {
  let inputChar = 0;
  if (document.getElementById('roll').value.length === 0) {
    alert('empty');
  } else {
    inputChar = document.getElementById('roll').value;
  }
  const word = document.getElementById('arrPrint').innerText;
  const indexes = finedIndexes(word, inputChar);
  document.getElementById('footer_arrPrint').innerHTML = indexes;
  const include = fineInclude(word, inputChar);
  document.getElementById('footer_arrPrint2').innerHTML = include;
  const occurrences = countOccurrences(word, inputChar);
  if (occurrences > 0) {
    document.getElementById('footer_arrPrint3').innerHTML = occurrences;
  } else {
    document.getElementById('footer_arrPrint3').innerHTML = '';
  }
});

/**
 *A random word will be returned.
 *
 * @returns {string} number returned
 */
function rand () {
  const colors = ['Red', 'Blue', 'Green', 'Yellow', 'Brown', 'Purple', 'Beige', 'Gold', 'Orange', 'Pink', 'Violet', 'Cobalt', 'Grey', 'Indigo', 'Silver', 'Cyan', 'Lime', 'Magenta', 'White', 'Pearl'];
  return colors[Math.floor(Math.random() * colors.length)];
}
/**
 *The uppercase of the word will be returned.
 *
 * @param {string} str is passed to the function.
 * @returns {string} an string in uppercase will be returned.
 */
function toUpper (str) {
  return str.toUpperCase();
}
/**
 *The lowercase of the word will be returned.
 *
 * @param {string} str is passed to the function.
 * @returns {string} an string in lowercase will be returned.
 */
function toLower (str) {
  return str.toLowerCase();
}
/**
 *The number of consonants letter of the word will be returned.
 *
 * @param {string} str is passed to the function.
 * @returns {number} an string in consonants will be returned.
 */
function consonants (str) {
  return str.match(/[^aeiou]/gi);
}
/**
 *The number of vowels letter of the word will be returned.
 *
 * @param {string} str is passed to the function.
 * @returns {number} number of vowels letters will be returned.
 */
function vowels (str) {
  return str.match(/[aeiou]/gi);
}
/**
 *The number of word letters will be returned.
 *
 * @param {string} str is passed to the function.
 * @returns {number} The number of word letters will be returned.
 */
function counts (str) {
  return str.length;
}
/**
 *The function will check the letter existence in the word and returns the answer.
 *
 * @param {string} word will be passed to the function
 * @param {string} character will be passed to the function
 * @returns {string} returns the answer of letter existence in the word.
 */
function fineInclude (word, character) {
  if (word.includes(character)) {
    return 'YES';
  } else {
    return 'NO';
  }
}

/**
 *The function will check the letter indexes in the word and return the indexes numbers.
 *
 * @param {string} word will be passed to the function
 * @param {string} character will be passed to the function
 * @returns {number} returns the number of letter indexes in the word.
 */
function finedIndexes (word, character) {
  let i = -1;
  const check = [];
  while ((i = word.indexOf(character, i + 1)) >= 0) {
    check.push(i);
  }
  return check;
}
/**
 *The function will check the letter occurrences  in the word and return the number of it.
 *
 * @param {string} word will be passed to the function
 * @param {string} character will be passed to the function
 * @returns {number} return the number of letter occurrences in the word.
 */
function countOccurrences (word, character) {
  const count = word.split(character).length - 1;
  return count;
}
