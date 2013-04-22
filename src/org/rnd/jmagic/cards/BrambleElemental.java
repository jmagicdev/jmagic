package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bramble Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BrambleElemental extends Card
{
	public static final class EnchantExplode extends EventTriggeredAbility
	{
		public EnchantExplode(GameState state)
		{
			super(state, "Whenever an Aura becomes attached to Bramble Elemental or enters the battlefield attached to Bramble Elemental, put two 1/1 green Saproling creature tokens onto the battlefield.");

			SimpleEventPattern auraAttached = new SimpleEventPattern(EventType.ATTACH);
			auraAttached.put(EventType.Parameter.OBJECT, HasSubType.instance(SubType.AURA));
			auraAttached.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			this.addPattern(auraAttached);

			CreateTokensFactory tokens = new CreateTokensFactory(2, 1, 1, "Put two 1/1 green Saproling creature token onto the battlefield.");
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.SAPROLING);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public BrambleElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever an Aura becomes attached to Bramble Elemental, put two 1/1
		// green Saproling creature tokens onto the battlefield.
		this.addAbility(new EnchantExplode(state));
	}
}
