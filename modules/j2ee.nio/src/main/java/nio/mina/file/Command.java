package nio.mina.file;

import java.io.Serializable;

import utils.codec.ByteUtil;

/**
 * @author yuanwei
 * @version ctreateTime:2011-11-14 下午4:35:31
 */
public class Command implements Serializable{
	private static final long	serialVersionUID	=1L;

	private byte			command;
	private long			position;
	private long			size;
	private long			length;
	private String			path;
	private byte[]			bytes;

	public Command() {}
	public Command(byte command) {
		this.command=command;
	}
	public Command(byte command,String path) {
		this.command=command;
		this.path=path;
	}
	public Command(byte command,String path,long position,long size) {
		this.command=command;
		this.path=path;
		this.position=position;
		this.size=size;
	}
	public byte getCommand() {
		return command;
	}
	public void setCommand(byte command) {
		this.command=command;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path=path;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes=bytes;
	}
	public long getPosition() {
		return position;
	}
	public void setPosition(long position) {
		this.position=position;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size=size;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length=length;
	}
	public String getCommandName(byte cmd) {
		if(cmd==Constants.Cmd.START){return "START";}
		else if(cmd==Constants.Cmd.START){return "START";}
		else if(cmd==Constants.Cmd.DONE){return "DONE";}
		else if(cmd==Constants.Cmd.ERROR){return "ERROR";}
		else if(cmd==Constants.Cmd.QUIT){return "QUIT";}
		else if(cmd==Constants.Cmd.READ){return "READ";}
		else if(cmd==Constants.Cmd.WRITE){return "WRITE";}
		else if(cmd==Constants.Cmd.INFO){return "INFO";}

		return "UNKNOWN";
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("\"Command\":[\"");
		builder.append(getCommandName(command));
		builder.append("\",\"");
		builder.append(path);
		builder.append("\",\"");
		builder.append(ByteUtil.toHexOmitString(bytes,16));
		builder.append("\",\"");
		builder.append(position);
		builder.append("\",\"");
		builder.append(size);
		builder.append("\",\"");
		builder.append(length);
		builder.append("\"]");
		return builder.toString();
	}

}
