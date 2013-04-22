package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Trostani, Selesnya's Voice")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DRYAD})
@ManaCost("GGWW")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class TrostaniSelesnyasVoice extends Card
{
	public static final class TrostaniSelesnyasVoiceAbility0 extends EventTriggeredAbility
	{
		public TrostaniSelesnyasVoiceAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, you gain life equal to that creature's toughness.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			this.addEffect(youMay(gainLife(You.instance(), ToughnessOf.instance(thatCreature), "You gain life equal to that creature's toughness."), "You may gain life equal to that creature's toughness."));
		}
	}

	public static final class TrostaniSelesnyasVoiceAbility1 extends ActivatedAbility
	{
		public TrostaniSelesnyasVoiceAbility1(GameState state)
		{
			super(state, "(1)(G)(W), (T): Populate.");
			this.setManaCost(new ManaPool("(1)(G)(W)"));
			this.costsTap = true;
			this.addEffect(populate());
		}
	}

	public TrostaniSelesnyasVoice(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Whenever another creature enters the battlefield under your control,
		// you gain life equal to that creature's toughness.
		this.addAbility(new TrostaniSelesnyasVoiceAbility0(state));

		// (1)(G)(W), (T): Populate. (Put a token onto the battlefield that's a
		// copy of a creature token you control.)
		this.addAbility(new TrostaniSelesnyasVoiceAbility1(state));
	}
}
