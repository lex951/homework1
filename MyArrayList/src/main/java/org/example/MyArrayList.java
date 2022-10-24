package org.example;

import java.util.*;

public class MyArrayList<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elementData;
    private int size;

    public MyArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        if (capacity >= 0) {
            this.elementData = new Object[capacity];
        } else {
            throw new IllegalArgumentException();
        }
    }

    public MyArrayList(Collection<? extends E> c) {
        this(c.size());
        Object[] collectionToArray = c.toArray();
        size = collectionToArray.length;
        System.arraycopy(collectionToArray, 0, elementData, 0, size);
    }

    public void bubbleSort(Comparator<? super E> c) {
        boolean isSorted = true;
        while (isSorted) {
            isSorted = false;
            for (int i = 0; i < size - 1; i++) {
                if (
                        c.compare((E) elementData[i], (E) elementData[i + 1]) > 0
                ) {
                    Object tmp = elementData[i];
                    elementData[i] = elementData[i + 1];
                    elementData[i + 1] = tmp;
                    isSorted = true;
                }

            }
        }
    }

    public void quickSort(Comparator<? super E> c) {
        quickSortMethod(elementData, 0, size - 1, (Comparator<Object>) c);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean add(E e) {
        if (elementData.length <= size) {
            increaseSize();
        }
        elementData[size] = e;
        size++;
        return true;
    }

    public void add(int index, E element) {
        if (elementData.length <= size) {
            increaseSize();
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;

    }


    @Override
    public boolean remove(Object o) {
        for (int index = 0; index < size; index++) {
            if (o.equals(elementData[index])) {
                System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
                size--;
                elementData[size] = null;
                return true;
            }
        }
        return false;
    }

    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        size--;
        elementData[size] = null;
        return oldValue;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object o : c)
            add((E) o);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c)
            remove((E) o);
        return true;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        MyArrayList<Object> a = new MyArrayList();
        for (Object o : c) {
            if (this.contains(o))
                a.add(o);
        }
        elementData = a.toArray();
        return true;
    }

    @Override
    public void clear() {
        for (int index = size - 1; index >= 0; size--)
            elementData[index] = null;
    }

    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    private int indexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void checkIndex(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();
    }

    private void increaseSize() {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity * 3 / 2;
        Object[] oldElementData = elementData;
        Object[] newElementData = new Object[newCapacity];
        System.arraycopy(oldElementData, 0, newElementData, 0, size);
        elementData = newElementData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyArrayList<?> that)) return false;

        if (size != that.size) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(elementData, that.elementData);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(elementData);
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    private class Itr implements Iterator<E> {

        int index = 0;

        public Itr() {
        }

        @Override
        public boolean hasNext() {

//            return index < size;
            return index < size && elementData[index] != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (E) elementData[index++];
        }

        @Override
        public void remove() {
            System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
            size--;
            elementData[size] = null;
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        public boolean hasPrevious() {
            return index > 0 && elementData[index - 1] != null;
        }

        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();

            return (E) elementData[--index];
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void set(E e) {
            MyArrayList.this.set(index, e);
            index++;
        }

        @Override
        public void add(E e) {
            MyArrayList.this.add(index, e);
        }

    }
    private static void quickSortMethod(Object[] source, int from, int to, Comparator<Object> c) {
        int leftMarker = from;
        int rightMarker = to;
        Object pivot = source[(leftMarker + rightMarker) / 2];
        do {
            // Двигаем левый маркер слева направо пока элемент меньше, чем pivot
            while (c.compare(source[leftMarker], pivot) < 0) {
                leftMarker++;
            }
            // Двигаем правый маркер, пока элемент больше, чем pivot
            while (c.compare(source[rightMarker], pivot) > 0) {
                rightMarker--;
            }
            // Проверим, не нужно обменять местами элементы, на которые указывают маркеры
            if (leftMarker <= rightMarker) {
                // Левый маркер будет меньше правого только если мы должны выполнить swap
                if (leftMarker < rightMarker) {
                    Object tmp = source[leftMarker];
                    source[leftMarker] = source[rightMarker];
                    source[rightMarker] = tmp;
                }
                // Сдвигаем маркеры, чтобы получить новые границы
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // Выполняем рекурсивно для частей
        if (leftMarker < to) {
            quickSortMethod(source, leftMarker, to, c);
        }
        if (from < rightMarker) {
            quickSortMethod(source, from, rightMarker, c);
        }
    }
}
