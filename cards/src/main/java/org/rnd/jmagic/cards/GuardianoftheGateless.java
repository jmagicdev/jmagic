package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Guardian of the Gateless")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GuardianoftheGateless extends Card
{
	public static final class GuardianoftheGatelessAbility1 extends StaticAbility
	{
		public GuardianoftheGatelessAbility1(GameState state)
		{
			super(state, "Guardian of the Gateless can block any number of creatures.");

			// Palace Guard can block any number of creatures.
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_ANY_NUMBER_OF_CREATURES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public static final class GuardianoftheGatelessAbility2 extends EventTriggeredAbility
	{
		public GuardianoftheGatelessAbility2(GameState state)
		{
			super(state, "Whenever Guardian of the Gateless blocks, it gets +1/+1 until end of turn for each creature it's blocking.");
			this.addPattern(whenThisBlocks());

			SetGenerator num = Count.instance(BlockedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, num, num, "It gets +1/+1 until end of turn for each creature it's blocking."));
		}
	}

	public GuardianoftheGateless(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Guardian of the Gateless can block any number of creatures.
		this.addAbility(new GuardianoftheGatelessAbility1(state));

		// Whenever Guardian of the Gateless blocks, it gets +1/+1 until end of
		// turn for each creature it's blocking.
		this.addAbility(new GuardianoftheGatelessAbility2(state));
	}
}
