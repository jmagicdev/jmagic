package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mox Diamond")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.RELICS, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.STRONGHOLD, r = Rarity.RARE)})
@ColorIdentity({})
public final class MoxDiamond extends Card
{
	public static final class Drawback extends StaticAbility
	{
		public Drawback(GameState state)
		{
			super(state, "If Mox Diamond would enter the battlefield, you may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.");

			ZoneChangeReplacementEffect discardALandInstead = new ZoneChangeReplacementEffect(state.game, "You may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.", true);
			discardALandInstead.addPattern(asThisEntersTheBattlefield());

			SetGenerator replacedMove = discardALandInstead.replacedByThis();

			SetGenerator cause = CauseOf.instance(replacedMove);
			SetGenerator controller = ControllerOf.instance(replacedMove);
			SetGenerator oldObject = OldObjectOf.instance(replacedMove);

			EventFactory discardLand = new EventFactory(EventType.DISCARD_CHOICE, "Discard a land card");
			discardLand.parameters.put(EventType.Parameter.CAUSE, cause);
			discardLand.parameters.put(EventType.Parameter.PLAYER, controller);
			discardLand.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			discardLand.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(InZone.instance(HandOf.instance(controller)), HasType.instance(Type.LAND)));

			EventFactory may = new EventFactory(EventType.PLAYER_MAY, "You may discard a land card");
			may.parameters.put(EventType.Parameter.PLAYER, controller);
			may.parameters.put(EventType.Parameter.EVENT, Identity.instance(discardLand));

			EventFactory battlefield = putOntoBattlefield(oldObject, "Put Mox Diamond onto the battlefield.");

			EventFactory sorry = new EventFactory(EventType.MOVE_OBJECTS, "Put it into its owner's graveyard");
			sorry.parameters.put(EventType.Parameter.CAUSE, cause);
			sorry.parameters.put(EventType.Parameter.TO, GraveyardOf.instance(OwnerOf.instance(oldObject)));
			sorry.parameters.put(EventType.Parameter.OBJECT, oldObject);

			EventFactory ifThenElse = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may discard a land card. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.");
			ifThenElse.parameters.put(EventType.Parameter.IF, Identity.instance(may));
			ifThenElse.parameters.put(EventType.Parameter.THEN, Identity.instance(battlefield));
			ifThenElse.parameters.put(EventType.Parameter.ELSE, Identity.instance(sorry));
			discardALandInstead.addEffect(ifThenElse);

			this.addEffectPart(replacementEffectPart(discardALandInstead));

			this.canApply = NonEmpty.instance();
		}
	}

	public MoxDiamond(GameState state)
	{
		super(state);

		// If Mox Diamond would enter the battlefield, you may discard a land
		// card instead. If you do, put Mox Diamond onto the battlefield. If you
		// don't, put it into its owner's graveyard.
		this.addAbility(new Drawback(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
