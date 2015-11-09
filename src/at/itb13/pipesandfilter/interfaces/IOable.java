package at.itb13.pipesandfilter.interfaces;


public interface IOable<in, out> extends Readable<out>, Writeable<in> {

}
