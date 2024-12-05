package us.chaoqun.cpu_performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class CpuPerformanceApplication {
	private static final int QUEUE_SIZE = 100;
	private final Queue<byte[]> memoryQueue = new ConcurrentLinkedQueue<>();
	private final AtomicLong totalAllocated = new AtomicLong(0);

	public static void main(String[] args) {
		SpringApplication.run(CpuPerformanceApplication.class, args);
	}

	@GetMapping("/cpu-test")
	public String cpuTest(@RequestParam(defaultValue = "1000000") int iterations) {
		long startTime = System.currentTimeMillis();
		
		double result = 0;
		for (int i = 0; i < iterations; i++) {
			result += Math.sqrt(i) * Math.sin(i) * Math.cos(i);
		}
		
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		
		return String.format("{\"t\":%08d,\"r\":%012.2f}", duration, result);
	}

	@GetMapping("/memory-test")
	public String memoryTest(@RequestParam(defaultValue = "100") int blockSizeMB) {
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory());
		
		if (freeMemory < (blockSizeMB * 1024L * 1024L * 2)) {
			int halfSize = memoryQueue.size() / 2;
			for (int i = 0; i < halfSize; i++) {
				memoryQueue.poll();
			}
			System.gc();
			freeMemory = runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory());
		}
		
		try {
			byte[] block = new byte[blockSizeMB * 1024 * 1024];
			memoryQueue.offer(block);
			
			while (memoryQueue.size() > QUEUE_SIZE) {
				memoryQueue.poll();
			}
			
			totalAllocated.addAndGet(blockSizeMB);
			
			return String.format(
				"{\"a\":%08d,\"q\":%03d,\"f\":%08d}",
				totalAllocated.get(),
				memoryQueue.size(),
				freeMemory / (1024 * 1024)
			);
		} catch (OutOfMemoryError e) {
			memoryQueue.clear();
			System.gc();
			
			return String.format(
				"{\"a\":%08d,\"q\":%03d,\"f\":%08d}",
				-1,
				0,
				runtime.freeMemory() / (1024 * 1024)
			);
		}
	}

	@GetMapping("/memory-reset")
	public String resetMemory() {
		memoryQueue.clear();
		totalAllocated.set(0);
		System.gc();
		
		Runtime runtime = Runtime.getRuntime();
		return String.format(
			"{\"status\":\"reset\",\"freeMemory\":%d,\"maxMemory\":%d}",
			runtime.freeMemory() / (1024 * 1024),
			runtime.maxMemory() / (1024 * 1024)
		);
	}

}
