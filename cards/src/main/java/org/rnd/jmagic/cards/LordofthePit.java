package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lord of the Pit")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4BBB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class LordofthePit extends Card
{
	public static final class RitualSacrifice extends EventTriggeredAbility
	{
		public RitualSacrifice(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice a creature other than Lord of the Pit. If you can't, Lord of the Pit deals 7 damage to you.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory sacrifice = sacrifice(You.instance(), 1, RelativeComplement.instance(CREATURES_YOU_CONTROL, thisCard), "Sacrifice a creature other than Lord of the Pit");
			EventFactory damage = permanentDealDamage(7, You.instance(), "Lord of the Pit deals 7 damage to you.");

			EventType.ParameterMap ifParameters = new EventType.ParameterMap();
			ifParameters.put(EventType.Parameter.IF, Identity.instance(sacrifice));
			ifParameters.put(EventType.Parameter.ELSE, Identity.instance(damage));
			this.addEffect(new EventFactory(EventType.IF_EVENT_THEN_ELSE, ifParameters, "Sacrifice a creature other than Lord of the Pit. If you can't, Lord of the Pit deals 7 damage to you."));
		}
	}

	public LordofthePit(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new RitualSacrifice(state));
	}
}
