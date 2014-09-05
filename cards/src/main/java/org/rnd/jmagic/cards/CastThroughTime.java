package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cast Through Time")
@Types({Type.ENCHANTMENT})
@ManaCost("4UUU")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class CastThroughTime extends Card
{
	public static final class Rebounding extends StaticAbility
	{
		public Rebounding(GameState state)
		{
			super(state, "Instant and sorcery spells you control have rebound.");

			SetGenerator affected = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), Intersect.instance(Spells.instance(), ControlledBy.instance(You.instance(), Stack.instance())));

			this.addEffectPart(addAbilityToObject(affected, org.rnd.jmagic.abilities.keywords.Rebound.class));
		}
	}

	public CastThroughTime(GameState state)
	{
		super(state);

		// Instant and sorcery spells you control have rebound.
		this.addAbility(new Rebounding(state));
	}
}
