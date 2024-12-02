package us.chaoqun.cpu_performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CpuPerformanceApplication {

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

}
