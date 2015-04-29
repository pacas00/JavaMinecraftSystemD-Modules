package net.petercashel.HTBModule;

import net.petercashel.jmsDd.util.ASMPlugin;
import net.petercashel.jmsDd.util.ASMTransformer;
import java.util.Iterator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class HTBInstallerASMPlug implements ASMPlugin {

	@SuppressWarnings({ "unused", "unchecked", "deprecation" })
	@Override
	public byte[] transform(String name, byte[] bytes) {

		//Client Installer
		{
			if (name.contentEquals("cpw.mods.fml.installer.ClientInstall") || name.contentEquals("cpw/mods/fml/installer/ClientInstall")
					|| name.contentEquals("net.minecraftforge.installer.ClientInstall") || name.contentEquals("net/minecraftforge/installer/ClientInstall")) 
			{
				ClassNode classNode = new ClassNode();
				ClassReader classReader = new ClassReader(bytes);
				classReader.accept(classNode, 0);
				String targetMethodName = "run";
				String targetMethodDesc = "(Ljava/io/File;)Z";

				String targetMethodName2 = "getSuccessMessage";
				String targetMethodDesc2 = "()Ljava/lang/String;";

				// find method to inject into
				Iterator<MethodNode> methods = classNode.methods.iterator();
				while(methods.hasNext())
				{
					MethodNode m = methods.next();
					int insert_index = -1; //IMonitor monitor = DownloadUtils.buildMonitor();
					int array_index = -1; //JsonField[] fields
					int arraysize_index = -1; //Size
					int target_index = -1; //SimpleInstaller DIR
					int name_index = -1; //profile name
					int name_index2 = -1; //profile name
					if (ASMTransformer.debug) System.out.println("Method Name: "+m.name + " Desc:" + m.desc);

					if (m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc)) {
						//System.out.println("Inside target method!");

						//Work out how to iterate though instructions and append more. TODO


						AbstractInsnNode currentNode = null;
						AbstractInsnNode targetNode = null;
						AbstractInsnNode targetNode2 = null;
						AbstractInsnNode targetNode3 = null;
						AbstractInsnNode targetNode4 = null;

						AbstractInsnNode targetNode5 = null;					
						AbstractInsnNode targetNode6 = null;


						Iterator<AbstractInsnNode> iter = m.instructions.iterator();

						int index = -1;

						//Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
						while (iter.hasNext())
						{
							index++;
							currentNode = iter.next();

							if (currentNode.getOpcode() == Opcodes.INVOKESTATIC 
									&& currentNode.getNext().getOpcode() == Opcodes.ICONST_1 
									&& currentNode.getNext().getNext().getOpcode() == Opcodes.ANEWARRAY 
									&& currentNode.getNext().getNext().getNext().getOpcode() == Opcodes.DUP 
									&& currentNode.getNext().getNext().getNext().getNext().getOpcode() == Opcodes.ICONST_0)
							{
								targetNode = currentNode;
								insert_index = index;
								if (ASMTransformer.debug) System.out.println("HIT! Installer Logic");

							}

							if (currentNode.getOpcode() == Opcodes.ASTORE && currentNode.getPrevious().getOpcode() == Opcodes.AASTORE
									&& currentNode.getPrevious().getPrevious().getOpcode() == Opcodes.INVOKESTATIC
									&& currentNode.getPrevious().getPrevious().getPrevious().getOpcode() == Opcodes.INVOKESTATIC
									&& currentNode.getPrevious().getPrevious().getPrevious().getPrevious().getOpcode() == Opcodes.INVOKESTATIC
									&& currentNode.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getOpcode() == Opcodes.LDC
									&& currentNode.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getOpcode() == Opcodes.ICONST_1
									&& currentNode.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getOpcode() == Opcodes.DUP
									)
							{
								targetNode2 = currentNode;
								array_index = index;
								if (ASMTransformer.debug) System.out.println("HIT! ProfileArgs");

							}

							if (currentNode.getOpcode() == Opcodes.ICONST_2 && currentNode.getNext().getOpcode() == Opcodes.ANEWARRAY
									&& currentNode.getNext().getNext().getOpcode() == Opcodes.DUP
									&& currentNode.getNext().getNext().getNext().getOpcode() == Opcodes.ICONST_0
									&& currentNode.getNext().getNext().getNext().getNext().getOpcode() == Opcodes.LDC
									&& currentNode.getNext().getNext().getNext().getNext().getNext().getOpcode() == Opcodes.INVOKESTATIC
									)
							{
								targetNode3 = currentNode;
								arraysize_index = index;
								if (ASMTransformer.debug) System.out.println("HIT! ProfileArgs Array Size");

							}

							if (currentNode.getOpcode() == Opcodes.INVOKESTATIC && currentNode.getNext().getOpcode() == Opcodes.INVOKESTATIC
									&& currentNode.getNext().getNext().getOpcode() == Opcodes.INVOKESTATIC

									&& currentNode.getPrevious().getOpcode() == Opcodes.LDC
									&& currentNode.getPrevious().getPrevious().getOpcode() == Opcodes.ICONST_0
									)
							{
								targetNode5 = currentNode;
								name_index = index;
								if (ASMTransformer.debug) System.out.println("HIT! NAME");

							}

							if (currentNode.getOpcode() == Opcodes.INVOKESTATIC && currentNode.getNext().getOpcode() == Opcodes.INVOKESTATIC
									&& currentNode.getNext().getNext().getOpcode() == Opcodes.ALOAD
									&& currentNode.getNext().getNext().getNext().getOpcode() == Opcodes.INVOKESTATIC

									&& currentNode.getPrevious().getOpcode() == Opcodes.ALOAD
									)
							{
								targetNode6 = currentNode;
								name_index2 = index;
								if (ASMTransformer.debug) System.out.println("HIT! NAME2");

							}
						}

						InsnList toInject2 = new InsnList();
						toInject2.add(new InsnNode(Opcodes.DUP));
						toInject2.add(new InsnNode(Opcodes.ICONST_2));
						toInject2.add(new LdcInsnNode("gameDir"));
						toInject2.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/petercashel/installer/Config", "getGameDir", "()Ljava/lang/String;"));
						toInject2.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "argo/jdom/JsonNodeFactories", "string", "(Ljava/lang/String;)Largo/jdom/JsonStringNode;"));
						toInject2.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "argo/jdom/JsonNodeFactories", "field", "(Ljava/lang/String;Largo/jdom/JsonNode;)Largo/jdom/JsonField;"));
						toInject2.add(new InsnNode(Opcodes.AASTORE));

						toInject2.add(new InsnNode(Opcodes.DUP));
						toInject2.add(new InsnNode(Opcodes.ICONST_3));
						toInject2.add(new LdcInsnNode("javaArgs"));
						toInject2.add(new LdcInsnNode("-Xmx2048M -Xms512M -XX:MaxPermSize=256M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M"));
						toInject2.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "argo/jdom/JsonNodeFactories", "string", "(Ljava/lang/String;)Largo/jdom/JsonStringNode;"));
						toInject2.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "argo/jdom/JsonNodeFactories", "field", "(Ljava/lang/String;Largo/jdom/JsonNode;)Largo/jdom/JsonField;"));
						toInject2.add(new InsnNode(Opcodes.AASTORE));

						m.instructions.insertBefore(targetNode2, toInject2);

						m.instructions.insertBefore(targetNode5, new MethodInsnNode(Opcodes.INVOKESTATIC, "net/petercashel/installer/Config", "getName", "()Ljava/lang/String;"));
						m.instructions.remove(targetNode5);
						m.instructions.insertBefore(targetNode6, new MethodInsnNode(Opcodes.INVOKESTATIC, "net/petercashel/installer/Config", "getName", "()Ljava/lang/String;"));
						m.instructions.remove(targetNode6);

						m.instructions.insertBefore(targetNode3, new InsnNode(Opcodes.ICONST_4));
						m.instructions.remove(targetNode3);

						InsnList toInject = new InsnList();
						//INVOKESTATIC net/petercashel/installer/InstallerMods.updateLogic ()V
						toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/petercashel/installer/InstallerMods", "updateLogic", "()V"));
						m.instructions.insertBefore(targetNode, toInject);

						if (ASMTransformer.debug) System.out.println("ClientInstall Patching Complete! (1)");
					}

				}

				Iterator<MethodNode> methods2 = classNode.methods.iterator();
				while(methods2.hasNext())
				{
					MethodNode m = methods2.next();
					int target_index = -1; //profile name // Success Message
					if (ASMTransformer.debug) System.out.println("Method Name: "+m.name + " Desc:" + m.desc);

					if (m.name.equals(targetMethodName2) && m.desc.equals(targetMethodDesc2)) {
						if (ASMTransformer.debug) System.out.println("Inside target method!");

						AbstractInsnNode currentNode = null;
						AbstractInsnNode targetNode = null;

						Iterator<AbstractInsnNode> iter = m.instructions.iterator();

						int index = -1;

						while (iter.hasNext())
						{
							index++;
							currentNode = iter.next();

							if (currentNode.getOpcode() == Opcodes.INVOKESTATIC && currentNode.getNext().getOpcode() == Opcodes.AASTORE
									&& currentNode.getNext().getNext().getOpcode() == Opcodes.DUP
									&& currentNode.getNext().getNext().getNext().getOpcode() == Opcodes.ICONST_1

									&& currentNode.getPrevious().getOpcode() == Opcodes.ICONST_0
									&& currentNode.getPrevious().getPrevious().getOpcode() == Opcodes.DUP
									)
							{
								targetNode = currentNode;
								target_index = index;
								if (ASMTransformer.debug) System.out.println("HIT! NAME3");

							}
						}

						m.instructions.insertBefore(targetNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "net/petercashel/installer/Config", "getName", "()Ljava/lang/String;"));
						m.instructions.remove(targetNode);

						if (ASMTransformer.debug) System.out.println("ClientInstall Patching Complete! (2)");
					}
				}

				//ASM specific for cleaning up and returning the final bytes for JVM processing.
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
				classNode.accept(writer);
				return writer.toByteArray();
			}
		}


		//SimpleInstaller / ServerInstall

		if (name.contentEquals("cpw.mods.fml.installer.SimpleInstaller") || name.contentEquals("cpw/mods/fml/installer/SimpleInstaller") || name.contentEquals("net.minecraftforge.installer.SimpleInstaller") || name.contentEquals("net/minecraftforge/installer/SimpleInstaller")) {
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(bytes);
			classReader.accept(classNode, 0);
			String targetMethodName = "handleOptions";
			String targetMethodDesc = "(Ljoptsimple/OptionParser;Ljoptsimple/OptionSet;Ljoptsimple/OptionSpecBuilder;Ljoptsimple/OptionSpecBuilder;Ljoptsimple/OptionSpecBuilder;)V";

			// find method to inject into
			Iterator<MethodNode> methods = classNode.methods.iterator();
			while(methods.hasNext())
			{
				MethodNode m = methods.next();
				int target_index = -1; //Size
				if (ASMTransformer.debug) System.out.println("Method Name: "+m.name + " Desc:" + m.desc);

				if (m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc)) {
					//System.out.println("Inside target method!");

					//Work out how to iterate though instructions and append more. TODO


					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					int index = -1;

					//Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
					while (iter.hasNext())
					{
						index++;
						currentNode = iter.next();

						if (currentNode.getOpcode() == Opcodes.LDC
								&& currentNode.getNext().getOpcode() == Opcodes.INVOKESPECIAL
								&& currentNode.getNext().getNext().getOpcode() == Opcodes.INVOKEVIRTUAL

								&& currentNode.getPrevious().getOpcode() == Opcodes.DUP
								&& currentNode.getPrevious().getPrevious().getOpcode() == Opcodes.NEW
								)
						{
							targetNode = currentNode;
							target_index = index;
							//System.out.println("HIT! TARGET");
							//GOTTA REPLACE HERE! MULTIPLE HITS!
							InsnList toInject = new InsnList();
							toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/petercashel/installer/Config", "getServerDir", "()Ljava/lang/String;"));	
							m.instructions.insertBefore(targetNode, toInject);
							m.instructions.remove(targetNode);


						}
					}



					if (ASMTransformer.debug) System.out.println("SimpleInstaller Patching Complete!");
				}
			}

			//ASM specific for cleaning up and returning the final bytes for JVM processing.
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(writer);
			return writer.toByteArray();
		}

		return bytes;
	}

}
