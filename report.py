import pandas as pd
import matplotlib.pyplot as plt

data = {
    "Metric": [
        "Requests per second (#/sec)", 
        "Time per request (ms, mean)", 
        "Time per request (ms, mean, across all concurrent requests)", 
        "Transfer rate (Kbytes/sec received)", 
        "Median response time (ms)", 
        "Longest request time (ms)"
    ],
    "m6i.8xlarge": [310.99, 321.550, 3.215, 49.81, 260, 2074],
    "m7i.8xlarge": [381.83, 261.897, 2.619, 61.15, 199, 1454]
}

df = pd.DataFrame(data)

print(df)

metrics = data["Metric"]
m6i = data["m6i.8xlarge"]
m7i = data["m7i.8xlarge"]

plt.figure(figsize=(10, 6))
x = range(len(metrics))

plt.bar(x, m6i, width=0.4, label="m6i.8xlarge", align="center")
plt.bar([i + 0.4 for i in x], m7i, width=0.4, label="m7i.8xlarge", align="center")

plt.xticks([i + 0.2 for i in x], metrics, rotation=45, ha="right")
plt.ylabel("Value")
plt.title("Performance Benchmark Comparison (m6i.8xlarge vs m7i.8xlarge)")
plt.legend()
plt.tight_layout()

plt.savefig("benchmark_comparison.png")

plt.show()

