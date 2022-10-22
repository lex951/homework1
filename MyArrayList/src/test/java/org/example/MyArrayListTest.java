package org.example;


import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MyArrayListTest {

    MyArrayList<String> expected;
    MyArrayList<String> actual;


    @BeforeEach
    void setUp() {
        actual = new MyArrayList<>();
        expected = new MyArrayList<>();

        actual.add("A");
        actual.add("B");
        actual.add("C");
    }

    @Test
    void size() {
        assertThat(actual.size()).isEqualTo(3);

        actual.add("D");

        assertThat(actual.size()).isEqualTo(4);
    }

    @Test
    void isEmpty() {
        actual = new MyArrayList<>();

        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    void contains() {
        assertThat(actual.contains("A")).isTrue();
        assertThat(actual.contains("X")).isFalse();
    }

    @Test
    void add() {
        assertThat(actual.add("Z")).isTrue();
        assertThat(actual.contains("Z")).isTrue();
    }

    @Test
    void testAddWithParam() {
        actual.add(0, "X");
        assertThat(actual.get(0)).isEqualTo("X");
    }

    @Test
    void get() {
        assertThat(actual.get(0)).isEqualTo("A");
        assertThat(actual.get(1)).isEqualTo("B");
        assertThat(actual.get(2)).isEqualTo("C");
    }

    @Test
    void set() {
        String oldElement = actual.get(0);
        assertThat(actual.set(0, "AA")).isEqualTo(oldElement);
        assertThat(actual.get(0)).isEqualTo("AA");

    }

    @Test
    void removeObject() {
        expected.add("A");
        expected.add("B");
        actual.remove("C");
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void removeByIndex() {
        expected.add("A");
        expected.add("B");
        expected.add("C");
        actual.remove(0);
        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void containsAll() {
        expected.add("A");
        expected.add("B");
        assertThat(actual.containsAll(expected)).isTrue();
    }

    @Test
    void addAll() {
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("Z");
        expected.add("X");
        List<String> testList = new ArrayList<>();
        testList.add("Z");
        testList.add("X");
        actual.addAll(testList);
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void removeAll() {
        List<String> testList = new ArrayList<>();
        testList.add("A");
        testList.add("B");
        testList.add("C");
        actual.removeAll(testList);
        assertThat(actual.isEmpty()).isTrue();
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void indexOf() {
        assertThat(actual.indexOf("A")).isEqualTo(0);
        assertThat(actual.indexOf("B")).isEqualTo(1);
        assertThat(actual.indexOf("C")).isEqualTo(2);
    }
}