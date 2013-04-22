package org.rnd.jmagic.engine.gameTypes;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface DependsOn
{
	public Class<? extends org.rnd.jmagic.engine.GameType.GameTypeRule> value();
}
