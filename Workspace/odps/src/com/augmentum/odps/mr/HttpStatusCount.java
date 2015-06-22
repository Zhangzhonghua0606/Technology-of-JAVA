package com.augmentum.odps.mr;

import java.io.IOException;
import java.util.Iterator;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.TableInfo;
import com.aliyun.odps.mapred.JobClient;
import com.aliyun.odps.mapred.MapperBase;
import com.aliyun.odps.mapred.ReducerBase;
import com.aliyun.odps.mapred.RunningJob;
import com.aliyun.odps.mapred.conf.JobConf;
import com.aliyun.odps.mapred.utils.InputUtils;
import com.aliyun.odps.mapred.utils.OutputUtils;
import com.aliyun.odps.mapred.utils.SchemaUtils;

public class HttpStatusCount {

	public static class TokenizerMapper extends MapperBase {
		Record status;
		Record one;
		
		@Override
		public void setup(TaskContext context) throws IOException {
			status = context.createMapOutputKeyRecord();
			one = context.createMapOutputValueRecord();
			one.set(new Object[] {1L});
		}
		
		@Override
		public void map(long key, Record record, TaskContext context)
				throws IOException {
				Long value =(Long) record.get(1);
				status.set(new Object[] {value});
		        
		        context.write(status, one);
		}
	}
	
	
	// A combiner class that combines map output by sum them.
	public static class SumCombiner extends ReducerBase {
		private Record count;
		
		@Override
		public void setup(TaskContext context)
				throws IOException {
			count = context.createMapOutputValueRecord();
		}
		
		@Override
		public void reduce(Record key, Iterator<Record> values,
				TaskContext context) throws IOException {
			long c = 0;
			while (values.hasNext()) {
				Record value = values.next();
				c += (Long) value.get(0);
			}
			count.set(0, c);
			context.write(key, count);
		}
	}
	
	// A reducer class that just emits the sum of the input values.
	public static class SumReducer extends ReducerBase {
		private Record result = null;
		
		@Override
		public void setup(TaskContext context)
				throws IOException {
			result = context.createOutputRecord();
		}
		
		@Override
		public void reduce(Record key, Iterator<Record> values,
				TaskContext context)
				throws IOException {
			long count = 0;
			while (values.hasNext()) {
				Record value = values.next();
				count += (Long) value.get(0);
			}
			result.set(0, key.get(0));
			result.set(1, count);

		    context.write(result);
		}
	}
	
	public static void main(String[] args) throws Exception {
		JobConf job = new JobConf();
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(SumCombiner.class);
		job.setReducerClass(SumReducer.class);
		
		job.setMapOutputKeySchema(SchemaUtils.fromString("status:bigint"));
		job.setMapOutputValueSchema(SchemaUtils.fromString("count:bigint"));
		
		String date = "20150602";
		InputUtils.addTable(TableInfo.builder().tableName(args[0]).partSpec("operation_date=" + date).build(), job);
		OutputUtils.addTable(TableInfo.builder().tableName(args[1]).build(), job);
		
		RunningJob rj = JobClient.runJob(job);
		rj.waitForCompletion();
	}
}
