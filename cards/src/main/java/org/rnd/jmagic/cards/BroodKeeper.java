package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Brood Keeper")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class BroodKeeper extends Card
{
	public static final class BroodKeeperAbility0 extends EventTriggeredAbility
	{
		public BroodKeeperAbility0(GameState state)
		{
			super(state, "Whenever an Aura becomes attached to Brood Keeper, put a 2/2 red Dragon creature token with flying onto the battlefield. It has \"(R): This creature gets +1/+0 until end of turn.\"");

			SimpleEventPattern auraAttached = new SimpleEventPattern(EventType.ATTACH);
			auraAttached.put(EventType.Parameter.OBJECT, HasSubType.instance(SubType.AURA));
			auraAttached.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			this.addPattern(auraAttached);

			CreateTokensFactory dragon = new CreateTokensFactory(1, 2, 2, "Put a 2/2 red Dragon creature token with flying onto the battlefield. It has \"(R): This creature gets +1/+0 until end of turn.\"");
			dragon.setColors(Color.RED);
			dragon.setSubTypes(SubType.DRAGON);
			dragon.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			dragon.addAbility(org.rnd.jmagic.abilities.Firebreathing.class);
			this.addEffect(dragon.getEventFactory());
		}
	}

	public BroodKeeper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever an Aura becomes attached to Brood Keeper, put a 2/2 red
		// Dragon creature token with flying onto the battlefield. It has
		// "(R): This creature gets +1/+0 until end of turn."
		this.addAbility(new BroodKeeperAbility0(state));
	}
}
