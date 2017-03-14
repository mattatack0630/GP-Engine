package utils;

import java.util.List;

/**
 * Created by mjmcc on 12/20/2016.
 */
public class Sorter
{
	public static <E extends Comparable> List<E> insertionSort(List<E> input)
	{

		for (int i = 1; i < input.size(); i++)
		{
			for (int j = i; j > 0; j--)
			{
				if (input.get(j).compareTo(input.get(j - 1)) == 1)
				{
					E temp = input.get(j);
					input.set(j, input.get(j - 1));
					input.set(j - 1, temp);
				}
			}
		}
		return input;
	}

	public static <E extends Comparable> List<E> bubbleSort(List<E> list)
	{
		return null;
	}

	public static <E extends Comparable> List<E> quickSort(List<E> list)
	{
		return null;
	}

	public static <E extends Comparable> List<E> heapSort(List<E> list)
	{
		return null;
	}

	public static <E extends Comparable> List<E> dynamicSort(List<E> start, E element)
	{
		int predIndex = start.isEmpty() ? 0 : binaryFindPred(start, 0, start.size() - 1, element);
		start.add(predIndex, element);
		return start;
	}

	public static <E extends Comparable> int binaryFindPred(List<E> list, int start, int end, E findEl)
	{
		// ordered least -> greatest
		int midIndex = (start + end) / 2;
		E elMid = list.get(midIndex); // check access speed
		int c = elMid.compareTo(findEl);

		if (start >= end)
			return (c == -1) ? midIndex + 1 : midIndex;
		if (c == -1)
			return binaryFindPred(list, midIndex + 1, end, findEl);
		if (c == 1)
			return binaryFindPred(list, start, midIndex - 1, findEl);

		return (c == -1) ? midIndex + 1 : midIndex;
	}
}
