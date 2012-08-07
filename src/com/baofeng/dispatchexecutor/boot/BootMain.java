package com.baofeng.dispatchexecutor.boot;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.baofeng.dispatchexecutor.bean.ParamCommons;
import com.baofeng.dispatchexecutor.caller.gen.ExecutorService;
import com.baofeng.dispatchexecutor.caller.impl.ExecutorServiceImpl;
import com.baofeng.dispatchexecutor.utils.ProcessUtils;

public class BootMain {
	TServer server;
	private void startServer(int port) {
		try {
			TServerSocket serverTransport = new TServerSocket(port);
			ExecutorService.Processor processor = new ExecutorService.Processor(new ExecutorServiceImpl());
			Factory protFactory = new TBinaryProtocol.Factory(true, true);
			TThreadPoolServer.Args arg = new TThreadPoolServer.Args(serverTransport);
			arg.protocolFactory(protFactory);
			arg.processor(processor);
			server = new TThreadPoolServer(arg);
			System.out.println("Starting server pid:"+ProcessUtils.getPid()+" on port "+port+" ...");
			server.serve();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void stopServer(){
		if(this.server!=null && this.server.isServing()){
			this.server.stop();
		}
	}
	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("p","port",true,"start port");
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);
		ParamCommons.SERVICE_PORT =Integer.parseInt(cmd.getOptionValue("port").toString());
		BootMain main = new BootMain();
		main.startServer(ParamCommons.SERVICE_PORT);
	}
}
