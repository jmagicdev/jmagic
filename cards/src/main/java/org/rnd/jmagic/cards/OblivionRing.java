package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Oblivion Ring")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON), @Printings.Printed(ex = Lorwyn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class OblivionRing extends Card
{
	public static final class TheLordTakethAway extends EventTriggeredAbility
	{
		public TheLordTakethAway(GameState state)
		{
			super(state, "When Oblivion Ring enters the battlefield, exile another target nonland permanent.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator nonLandPermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
			Target target = this.addTarget(RelativeComplement.instance(nonLandPermanents, ABILITY_SOURCE_OF_THIS), "target nonland permanent other than Oblivion Ring");

			EventFactory exile = exile(targetedBy(target), "Exile another target nonland permanent.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(TheLordGivethBack.class);
		}
	}

	public static final class TheLordGivethBack extends EventTriggeredAbility
	{
		public TheLordGivethBack(GameState state)
		{
			super(state, "When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory returnCard = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return the exiled card to the battlefield under its owner's control.");
			returnCard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCard.parameters.put(EventType.Parameter.CONTROLLER, OwnerOf.instance(exiledCard));
			returnCard.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(returnCard);

			this.getLinkManager().addLinkClass(TheLordTakethAway.class);
		}
	}

	public OblivionRing(GameState state)
	{
		super(state);

		// When Oblivion Ring enters the battlefield, exile another target
		// nonland permanent.
		this.addAbility(new TheLordTakethAway(state));

		// When Oblivion Ring leaves the battlefield, return the exiled card to
		// the battlefield under its owner's control.
		this.addAbility(new TheLordGivethBack(state));
	}
}
