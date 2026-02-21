package org.konge.taskmanagementapp.api.service.common;

import org.konge.taskmanagementapp.api.model.common.Positionable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class PositioningService<T extends Positionable> {

    public static final double MAX_POS = Double.MAX_VALUE;
    public static final double MIN_POS = 0.0;

    /**
     * Determines element's position and keeps list's elements' positions consistent to the append.<br>
     * Changes in every other element modified that's not <i>element</i> get saved with the saving method.<br>
     * Requires list to be ordered by items' positions ascending.
     * @param list positionable elements list.
     * @param element element to append and save.
     * @param saver saving method.
     */
    public void insertLast(List<T> list, T element, Consumer<T> saver) {
        if (list.isEmpty()) {
            element.setPosition(MIN_POS);
            return;
        }

        element.setPosition(MAX_POS);

        if (list.size() == 1) {
            T first = list.getFirst();

            first.setPosition(MIN_POS);

            saver.accept(first);

            return;
        }

        T last = list.getLast();
        T secondToLast = list.get(list.size() - 2);

        last.setPosition(
                secondToLast.getPosition() + (MAX_POS - secondToLast.getPosition()) / 2
        );

        saver.accept(last);
    }

    /**
     * Requires list to have at least two elements.<br>
     * Sets item's position to be the first of the list, keeping the rest of the list's items' positions consistent and
     * saved through the saver method.<br>
     * This method does not save the item with the saver method.<br>
     * Requires list to be ordered by items' positions ascending.
     * @param item Item whose position will be set.
     * @param list List where item will later be available.
     * @param saver Saving method.
     */
    public void moveFirst(T item, List<T> list, Consumer<T> saver) {
        if (list.size() == 2) {
            if (item.equals(list.getLast())) {
                T first = list.getFirst();

                item.setPosition(MIN_POS);

                first.setPosition(MAX_POS);

                saver.accept(first);
            }

            return;
        }

        T first = list.getFirst();
        T second = list.get(1);

        first.setPosition(
                second.getPosition() / 2
        );

        item.setPosition(MIN_POS);

        saver.accept(first);
    }

    /**
     * Requires list to have at least two elements.<br>
     * Sets item's position to be the last of the list, keeping the rest of the list's items' positions consistent and
     * saved through the saver method.<br>
     * This method does not save the item with the saver method.<br>
     * Requires list to be ordered by items' positions ascending.
     * @param item Item whose position will be set.
     * @param list List where item will later be available.
     * @param saver Saving method.
     */
    public void moveLast(T item, List<T> list, Consumer<T> saver) {
        if (list.size() == 2){
            if (item.equals(list.getFirst())) {
                T last = list.getLast();

                item.setPosition(MAX_POS);

                last.setPosition(MIN_POS);

                saver.accept(last);
            }

            return;
        }

        T last = list.getLast();
        T secondToLast = list.get(list.size() - 2);

        last.setPosition(
                secondToLast.getPosition() + (MAX_POS - secondToLast.getPosition()) / 2
        );

        item.setPosition(MAX_POS);

        saver.accept(last);
    }

    public void moveBetween(Double prevItemPosition, Double nextItemPosition, T item) {
        item.setPosition(
                prevItemPosition + (nextItemPosition - prevItemPosition) / 2
        );
    }

}
