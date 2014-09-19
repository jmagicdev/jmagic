package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Prowess")
public final class Prowess extends Keyword
{
	public Prowess(GameState state)
	{
		super(state, "Prowess");
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new ProwessTrigger(this.state));
		return ret;
	}

	public static final class ProwessTrigger extends EventTriggeredAbility
	{
		public ProwessTrigger(GameState state)
		{
			super(state, "Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.");

			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, new NonTypePattern(Type.CREATURE));
			this.addPattern(cast);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "This creature gets +1/+1 until end of turn."));
		}
	}
}
