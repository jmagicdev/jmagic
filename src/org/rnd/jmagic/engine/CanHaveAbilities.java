package org.rnd.jmagic.engine;

public interface CanHaveAbilities
{
	public java.util.Collection<NonStaticAbility> getNonStaticAbilities();

	public java.util.Collection<Keyword> getKeywordAbilities();

	public java.util.Collection<StaticAbility> getStaticAbilities();
}
