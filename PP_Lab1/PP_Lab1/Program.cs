using System;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace PP_Lab1
{
    internal class Program
    {
        static int Sum(int start, int finish, int[] matrix)
        {
            int sum = 0;
            for (int i = start; i < finish; i++)
            {
                sum += matrix[i];
            }            
            return sum;
        }
        static int GeneralSum(int[] matrix, int threadCount)
        {
            int sumInPotock = matrix.Length / threadCount;
            Task<int>[] task = new Task<int>[threadCount];

            for (int i = 0; i < threadCount; i++)
            {
                int start = i* sumInPotock;
                int finish;
                if (i == threadCount - 1)
                {
                    finish = matrix.Length;
                }
                else
                {
                    finish = start+sumInPotock;
                }
                task[i] = Task.Run(()=> Sum(start,finish,matrix));
            }
            Task.WaitAll(task);
            int FinishSum = task.Sum(task => task.Result);
            return FinishSum;
        }
        static void Main(string[] args)
        {
            int threadCount = Environment.ProcessorCount;
            Console.WriteLine("Count threadCount: "+ threadCount);
            int[] matrix = new int[50000];
            for (int i = 0; i < matrix.Length; i++)
            {
                matrix[i] = i+1;
            }
            int sum = GeneralSum(matrix, threadCount);
            Console.WriteLine(sum);
        }
    }
}
