package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Sprouting Thrinax")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("BRG")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class SproutingThrinax extends Card
{
	public static final class Sprouting extends EventTriggeredAbility
	{
		public Sprouting(GameState state)
		{
			super(state, "When Sprouting Thrinax dies, put three 1/1 green Saproling creature tokens onto the battlefield.");

			this.addPattern(whenThisDies());

			CreateTokensFactory tokens = new CreateTokensFactory(3, 1, 1, "Put three 1/1 green Saproling creature tokens onto the battlefield.");
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.SAPROLING);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public SproutingThrinax(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Sprouting Thrinax is put into a graveyard from the battlefield,
		// put three 1/1 green Saproling creature tokens onto the battlefield.
		this.addAbility(new Sprouting(state));
	}
}
