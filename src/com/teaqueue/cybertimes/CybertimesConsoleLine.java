package com.teaqueue.cybertimes;

import java.util.Random;

public class CybertimesConsoleLine {
	static String[] verbs = new String[]{"abstracting", "accessing", "allocating", "analyzing", "assembling", "assigning", "archiving", "batching", "benchmarking", "branching", "browsing", "buffering", "building", "calculating", "casting", "changing", "collapsing", "collecting", "communicating", "comparing", "compiling", "compressing", "concatenating", "constructing", "copying", "crawling", "counting", "debugging", "declaring", "decompiling", "decrypting", "deduplicating", "defining", "defragmenting", "deleting", "denying", "deserializing", "destructing", "dumping", "emulating", "encapsulating", "encrypting", "engaging", "enumerating", "estimating", "evaluating", "executing", "expanding", "extracting", "filtering", "finalizing", "finding", "flushing", "forcing", "forking", "formatting", "fragmenting", "freeing", "generating", "grouping", "hacking", "hashing", "harvesting", "improving", "indexing", "inheriting", "initializing", "initiating", "inspecting", "instantiating", "integrating", "interpreting", "interrupting", "isolating", "iterating through", "joining", "limiting", "linking", "loading", "locking", "locking on to", "logging", "mapping", "measuring", "modifying", "nesting", "offloading", "optimizing", "parsing", "predicting", "processing", "purging", "quantizing", "querying", "receiving", "recovering", "recursing", "reducing", "refactoring", "referencing", "refining", "restructuring", "reticulating", "reverting", "routing", "running", "scheduling", "scrambling", "searching for", "sending", "serializing", "serving", "sharding", "shifting", "shuffling", "sifting", "simulating", "smoothing", "sorting", "spoofing", "spooling", "switching", "synthesizing", "tagging", "terminating", "testing", "toggling", "tracing", "transitioning", "translating", "tweaking", "unarchiving", "uniting", "updating", "virtualizing"};
	static String[] adjectives = new String[]{"archived", "artifacted", "best-guess", "binary", "cleartext", "cloud", "combined", "concurrent", "decimal", "deep", "deterministic", "distributed", "dynamic", "encrypted", "enhanced", "external", "fragmented", "geometric", "harvested", "hex", "hosted", "idle", "in-memory", "intelligent", "internal", "jagged", "linear", "linked", "local", "managed", "networked", "neural", "new", "numerical", "on-disk", "optimized", "parallel", "personal", "physical", "recursive", "redundant", "relative", "remote", "salted", "secure", "self-modifying", "serial", "sharded", "spanning", "stand-alone", "static", "surrogate", "temporary", "visual", "viral", "virtual"};
	static String[] nouns = new String[]{"addresses", "algorithms", "archives", "arguments", "arrays", "attributes", "bandwidth", "binaries", "blocks", "buffers", "bytes", "cells", "certificates", "ciphers", "classes", "clients", "code", "content", "data", "documents", "drivers", "elements", "errors", "events", "exceptions", "fiber", "files", "flags", "frames", "functions", "headers", "heaps", "hosts", "indices", "instances", "jobs", "kernels", "keys", "libraries", "logs", "loops", "machines", "metadata", "memory", "methods", "metrics", "modules", "mutexes", "nodes", "objects", "operations", "packets", "pages", "paradigms", "passwords", "phases", "plug-ins", "pointers", "procedures", "processes", "proxies", "protocol", "queries", "queues", "runtimes", "records", "references", "registers", "repositories", "resources", "rows", "scripts", "services", "sites", "sockets", "source", "splines", "stacks", "state", "statistics", "strings", "structs", "syntax", "systems", "tables", "tags", "text", "threads", "types", "users", "values", "variables", "vectors", "vertices", "viruses", "workers"};
	static Random random = new Random();
	static enum Type {PROGRESS_BAR, ELLIPSE, CYCLING_ELLIPSE, PERCENT};
	String string;
	Type type;
	int age, lifespan;
	
	public CybertimesConsoleLine() {
		// Set string.
		if (random.nextDouble() < .1)
			string = randomVerb() + " " + randomAdjective() + " " + randomNoun();
		else
			string = randomVerb() + " " + randomNoun();
		
		// Type selection.
		double selector = random.nextDouble();
		if (selector < .1)
			type = Type.PROGRESS_BAR;
		else if (selector < .4)
			type = Type.ELLIPSE;
		else if (selector < .7)
			type = Type.CYCLING_ELLIPSE;
		else
			type = Type.PERCENT;
		
		// Timing.
		age = 0;
		lifespan = 15 + random.nextInt(60);
	}
	private String randomVerb() {
		final String verb = verbs[random.nextInt(verbs.length)];
		return Character.toUpperCase(verb.charAt(0)) + verb.substring(1);
	}
	private String randomAdjective() {
		return adjectives[random.nextInt(adjectives.length)];
	}
	private String randomNoun() {
		return nouns[random.nextInt(nouns.length)];
	}
	
	public void update() {
		age++;
	}
	public boolean isDone() {
		return age >= lifespan;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (type == Type.PROGRESS_BAR) {
			builder.append('[');
			int ticks = age * 10 / lifespan;
			for (int i = 0; i < ticks; i++)
				builder.append('=');
			for (int i = ticks; i < 10; i++)
				builder.append(' ');
			builder.append("] ");
			builder.append(string);
			builder.append("...");
		} else if (type == Type.ELLIPSE) {
			builder.append(string);
			int dots = age / 5;
			for (int i = 0; i < dots; i++)
				builder.append('.');
		} else if (type == Type.CYCLING_ELLIPSE) {
			builder.append(string);
			int dots = age == lifespan ? 3 : (age / 3) % 4;
			for (int i = 0; i < dots; i++)
				builder.append('.');
		} else if (type == Type.PERCENT) {
			builder.append('[');
			int percentage = (int) Math.round((float)age / lifespan * 100);
			builder.append(percentage);
			builder.append("%] ");
			builder.append(string);
			builder.append("...");
		} else
			builder.append("ERROR: UNKNOWN CONSOLE LINE TYPE.");
		
		if (age == lifespan)
			builder.append(" DONE");
		return builder.toString();
	}
}
