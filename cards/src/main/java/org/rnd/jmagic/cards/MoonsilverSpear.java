package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Moonsilver Spear")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({})
public final class MoonsilverSpear extends Card
{
	public static final class MoonsilverSpearAbility0 extends StaticAbility
	{
		public MoonsilverSpearAbility0(GameState state)
		{
			super(state, "Equipped creature has first strike.");

			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public static final class MoonsilverSpearAbility1 extends EventTriggeredAbility
	{
		public MoonsilverSpearAbility1(GameState state)
		{
			super(state, "Whenever equipped creature attacks, put a 4/4 white Angel creature token with flying onto the battlefield.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, EquippedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addPattern(pattern);

			CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a 4/4 white Angel creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.ANGEL);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public MoonsilverSpear(GameState state)
	{
		super(state);

		// Equipped creature has first strike.
		this.addAbility(new MoonsilverSpearAbility0(state));

		// Whenever equipped creature attacks, put a 4/4 white Angel creature
		// token with flying onto the battlefield.
		this.addAbility(new MoonsilverSpearAbility1(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
