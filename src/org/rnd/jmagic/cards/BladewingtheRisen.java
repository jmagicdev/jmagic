package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bladewing the Risen")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON, SubType.ZOMBIE})
@ManaCost("3BBRR")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.DRAGONS, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class BladewingtheRisen extends Card
{
	public static final class BladewingtheRisenAbility1 extends EventTriggeredAbility
	{
		public BladewingtheRisenAbility1(GameState state)
		{
			super(state, "When Bladewing the Risen enters the battlefield, you may return target Dragon permanent card from your graveyard to the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator dragons = HasSubType.instance(SubType.DRAGON);
			SetGenerator permanentCards = HasType.instance(Type.permanentTypes());
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator dragonPermanentsInYourGraveyard = Intersect.instance(dragons, permanentCards, inYourGraveyard);
			SetGenerator target = targetedBy(this.addTarget(dragonPermanentsInYourGraveyard, "target Dragon permanent card from your graveyard"));

			EventFactory rise = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target Dragon permanent card from your graveyard to the battlefield");
			rise.parameters.put(EventType.Parameter.CAUSE, This.instance());
			rise.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			rise.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(youMay(rise, "You may return target Dragon permanent card from your graveyard to the battlefield."));

		}
	}

	public static final class BladewingtheRisenAbility2 extends ActivatedAbility
	{
		public BladewingtheRisenAbility2(GameState state)
		{
			super(state, "(B)(R): Dragon creatures get +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(B)(R)"));
		}
	}

	public BladewingtheRisen(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Bladewing the Risen enters the battlefield, you may return
		// target Dragon permanent card from your graveyard to the battlefield.
		this.addAbility(new BladewingtheRisenAbility1(state));

		// (B)(R): Dragon creatures get +1/+1 until end of turn.
		this.addAbility(new BladewingtheRisenAbility2(state));
	}
}
