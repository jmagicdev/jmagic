package org.rnd.jmagic.engine;

public class SimpleAbilityFactory implements AbilityFactory
{
	private Class<?> clazz;

	public SimpleAbilityFactory(Class<?> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public Identified create(GameState state, Identified thisObject)
	{
		Class<?>[] classArray = new Class[] {GameState.class};
		Object[] objectArray = new Object[] {state.game.physicalState};
		return (Identified)org.rnd.util.Constructor.construct(this.clazz, classArray, objectArray);
	}

	@Override
	public Class<?> clazz()
	{
		return this.clazz;
	}
}
