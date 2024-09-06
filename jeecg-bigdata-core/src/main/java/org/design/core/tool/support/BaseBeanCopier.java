package org.design.core.tool.support;

import org.design.core.tool.utils.BeanUtil;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Type;
import org.springframework.cglib.core.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/BaseBeanCopier.class */
public abstract class BaseBeanCopier {
    private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey) KeyFactory.create(BeanCopierKey.class);
    private static final Type CONVERTER = TypeUtils.parseType("org.springframework.cglib.core.Converter");
    private static final Type BEAN_COPIER = TypeUtils.parseType(BaseBeanCopier.class.getName());
    private static final Signature COPY = new Signature("copy", Type.VOID_TYPE, new Type[]{Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER});
    private static final Signature CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");

    /* access modifiers changed from: package-private */
    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/BaseBeanCopier$BeanCopierKey.class */
    public interface BeanCopierKey {
        Object newInstance(String str, String str2, boolean z);
    }

    public abstract void copy(Object obj, Object obj2, Converter converter);

    public static BaseBeanCopier create(Class source, Class target, boolean useConverter) {
        return create(source, target, null, useConverter);
    }

    public static BaseBeanCopier create(Class source, Class target, ClassLoader classLoader, boolean useConverter) {
        Generator gen;
        if (classLoader == null) {
            gen = new Generator();
        } else {
            gen = new Generator(classLoader);
        }
        gen.setSource(source);
        gen.setTarget(target);
        gen.setUseConverter(useConverter);
        return gen.create();
    }

    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/support/BaseBeanCopier$Generator.class */
    public static class Generator extends AbstractClassGenerator {
        private static final AbstractClassGenerator.Source SOURCE = new AbstractClassGenerator.Source(BaseBeanCopier.class.getName());
        private final ClassLoader classLoader;
        private Class source;
        private Class target;
        private boolean useConverter;

        Generator() {
            super(SOURCE);
            this.classLoader = null;
        }

        Generator(ClassLoader classLoader) {
            super(SOURCE);
            this.classLoader = classLoader;
        }

        public void setSource(Class source) {
            if (!Modifier.isPublic(source.getModifiers())) {
                setNamePrefix(source.getName());
            }
            this.source = source;
        }

        public void setTarget(Class target) {
            if (!Modifier.isPublic(target.getModifiers())) {
                setNamePrefix(target.getName());
            }
            this.target = target;
        }

        public void setUseConverter(boolean useConverter) {
            this.useConverter = useConverter;
        }

        protected ClassLoader getDefaultClassLoader() {
            return this.target.getClassLoader();
        }

        protected ProtectionDomain getProtectionDomain() {
            return ReflectUtils.getProtectionDomain(this.source);
        }

        public BaseBeanCopier create() {
            return (BaseBeanCopier) create(BaseBeanCopier.KEY_FACTORY.newInstance(this.source.getName(), this.target.getName(), this.useConverter));
        }

        public void generateClass(ClassVisitor v) {
            Type sourceType = Type.getType(this.source);
            Type targetType = Type.getType(this.target);
            ClassEmitter ce = new ClassEmitter(v);
            ce.begin_class(46, 1, getClassName(), BaseBeanCopier.BEAN_COPIER, (Type[]) null, "<generated>");
            EmitUtils.null_constructor(ce);
            CodeEmitter e = ce.begin_method(1, BaseBeanCopier.COPY, (Type[]) null);
            PropertyDescriptor[] getters = BeanUtil.getBeanGetters(this.source);
            PropertyDescriptor[] setters = BeanUtil.getBeanSetters(this.target);
            Map<String, Object> names = new HashMap<>(16);
            for (PropertyDescriptor getter : getters) {
                names.put(getter.getName(), getter);
            }
            Local targetLocal = e.make_local();
            Local sourceLocal = e.make_local();
            e.load_arg(1);
            e.checkcast(targetType);
            e.store_local(targetLocal);
            e.load_arg(0);
            e.checkcast(sourceType);
            e.store_local(sourceLocal);
            for (PropertyDescriptor setter : setters) {
                PropertyDescriptor getter2 = (PropertyDescriptor) names.get(setter.getName());
                if (getter2 != null) {
                    MethodInfo read = ReflectUtils.getMethodInfo(getter2.getReadMethod());
                    MethodInfo write = ReflectUtils.getMethodInfo(setter.getWriteMethod());
                    if (this.useConverter) {
                        Type setterType = write.getSignature().getArgumentTypes()[0];
                        e.load_local(targetLocal);
                        e.load_arg(2);
                        e.load_local(sourceLocal);
                        e.invoke(read);
                        e.box(read.getSignature().getReturnType());
                        EmitUtils.load_class(e, setterType);
                        e.push(write.getSignature().getName());
                        e.invoke_interface(BaseBeanCopier.CONVERTER, BaseBeanCopier.CONVERT);
                        e.unbox_or_zero(setterType);
                        e.invoke(write);
                    } else if (compatible(getter2, setter)) {
                        e.load_local(targetLocal);
                        e.load_local(sourceLocal);
                        e.invoke(read);
                        e.invoke(write);
                    }
                }
            }
            e.return_value();
            e.end_method();
            ce.end_class();
        }

        private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
            return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
        }

        protected Object firstInstance(Class type) {
            return ReflectUtils.newInstance(type);
        }

        protected Object nextInstance(Object instance) {
            return instance;
        }
    }
}
