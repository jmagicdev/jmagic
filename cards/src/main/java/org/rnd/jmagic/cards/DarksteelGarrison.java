package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Darksteel Garrison")
@Types({Type.ARTIFACT})
@SubTypes({SubType.FORTIFICATION})
@ManaCost("2")
@ColorIdentity({})
public final class DarksteelGarrison extends Card
{
	public static final class Defense extends StaticAbility
	{
		public Defense(GameState state)
		{
			super(state, "Fortified land has indestructible.");
			this.addEffectPart(addAbilityToObject(FortifiedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public static final class Offense extends EventTriggeredAbility
	{
		public Offense(GameState state)
		{
			super(state, "Whenever fortified land becomes tapped, target creature gets +1/+1 until end of turn.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, FortifiedBy.instance(thisCard));
			this.addPattern(pattern);

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), +1, +1, "Target creature gets +1/+1 until end of turn."));
		}
	}

	public DarksteelGarrison(GameState state)
	{
		super(state);

		this.addAbility(new Defense(state));
		this.addAbility(new Offense(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fortify(state, "(3)"));
	}
}
