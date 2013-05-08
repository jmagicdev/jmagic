package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Leonin Relic-Warder")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.CLERIC})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class LeoninRelicWarder extends Card
{
	public static final class LeoninRelicWarderAbility0 extends EventTriggeredAbility
	{
		public LeoninRelicWarderAbility0(GameState state)
		{
			super(state, "When Leonin Relic-Warder enters the battlefield, you may exile target artifact or enchantment.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));

			EventFactory exile = exile(target, "Exile target artifact or enchantment.");
			exile.setLink(this);

			this.addEffect(youMay(exile, "You may exile target artifact or enchantment."));

			this.getLinkManager().addLinkClass(LeoninRelicWarderAbility1.class);
		}
	}

	public static final class LeoninRelicWarderAbility1 extends EventTriggeredAbility
	{
		public LeoninRelicWarderAbility1(GameState state)
		{
			super(state, "When Leonin Relic-Warder leaves the battlefield, return the exiled card to the battlefield under its owner's control.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return the exiled card to the battlefield under its owner's control.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, OwnerOf.instance(exiledCard));
			factory.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(factory);

			this.getLinkManager().addLinkClass(LeoninRelicWarderAbility0.class);
		}
	}

	public LeoninRelicWarder(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Leonin Relic-Warder enters the battlefield, you may exile target
		// artifact or enchantment.
		this.addAbility(new LeoninRelicWarderAbility0(state));

		// When Leonin Relic-Warder leaves the battlefield, return the exiled
		// card to the battlefield under its owner's control.
		this.addAbility(new LeoninRelicWarderAbility1(state));
	}
}
