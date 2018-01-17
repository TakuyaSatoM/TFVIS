
public class QuickSort {
	public static void main(String[] args) {
		int[] list = { 2, 9, 5, 4, 8, 1, 3 };
		quickSort(list);
		print(list);
	}

	public static void quickSort(int[] list) {
		recursiveQuickSort(list, 0, list.length - 1);
	}

	public static void recursiveQuickSort(int[] list, int first, int last) {
		if (first < last) {
			int pivotIndex = partition(list, first, last);
			recursiveQuickSort(list, first, pivotIndex - 1);
			recursiveQuickSort(list, pivotIndex + 1, last);
		}
	}

	public static int partition(int[] list, int first, int last) {
		int pivot = list[first];
		int front = first + 1;
		int back = last;

		while (front < back) {
			while (front <= back && list[front] <= pivot) {
				front++;
			}
			while (front <= back && pivot < list[back]) {
				back--;
			}

			if (front < back) {
				int tmp = list[front];
				list[front] = list[back];
				list[back] = tmp;
			}
		}

		while (front > back) {
			if (list.length <= front) {
				front--;
			} else if (list[front] >= pivot) {
				front--;
			}
		}

		if (list[front] <= pivot) {
			list[first] = list[front];
			list[front] = pivot;
		}
		return front;
	}

	public static void print(int[] list) {
		for (int j = 0; j < list.length; j++) {
			System.out.print(list[j] + " ");
		}
		System.out.println();
	}
}