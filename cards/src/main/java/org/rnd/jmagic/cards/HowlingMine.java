package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Howling Mine")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class HowlingMine extends Card
{
	public static final class Howl extends EventTriggeredAbility
	{
		public Howl(GameState state)
		{
			super(state, "At the beginning of each player's draw step, if Howling Mine is untapped, that player draws an additional card.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			this.interveningIf = RelativeComplement.instance(thisCard, Tapped.instance());

			this.addEffect(drawCards(thatPlayer, 1, "That player draws an additional card."));
		}
	}

	public HowlingMine(GameState state)
	{
		super(state);

		this.addAbility(new Howl(state));
	}
}
