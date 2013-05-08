package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shiv")
@Types({Type.PLANE})
@SubTypes({SubType.DOMINARIA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Shiv extends Card
{
	public static final class GrantFirebreathing extends StaticAbility
	{
		public GrantFirebreathing(GameState state)
		{
			super(state, "All creatures have \"(R): This creature gets +1/+0 until end of turn.\"");

			this.addEffectPart(addAbilityToObject(CreaturePermanents.instance(), org.rnd.jmagic.abilities.Firebreathing.class));

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class DragonChaos extends EventTriggeredAbility
	{
		public DragonChaos(GameState state)
		{
			super(state, "Whenever you roll (C), put a 5/5 red Dragon creature token with flying onto the battlefield.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			CreateTokensFactory token = new CreateTokensFactory(1, 5, 5, "Put a 5/5 red Dragon creature token with flying onto the battlefield.");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.DRAGON);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public Shiv(GameState state)
	{
		super(state);

		this.addAbility(new GrantFirebreathing(state));

		this.addAbility(new DragonChaos(state));
	}
}
