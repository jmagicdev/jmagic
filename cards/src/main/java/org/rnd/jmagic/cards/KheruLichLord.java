package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kheru Lich Lord")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ZOMBIE})
@ManaCost("3BGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class KheruLichLord extends Card
{
	public static final class KheruLichLordAbility0 extends EventTriggeredAbility
	{
		public KheruLichLordAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may pay (2)(B). If you do, return a creature card at random from your graveyard to the battlefield. It gains flying, trample, and haste. Exile that card at the beginning of your next end step. If it would leave the battlefield, exile it instead of putting it anywhere else.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory mayPay = youMayPay("(2)(B)");

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));

			EventFactory choose = new EventFactory(RANDOM, "Return a creature card at random from your graveyard");
			choose.parameters.put(EventType.Parameter.OBJECT, deadThings);

			EventFactory raise = putOntoBattlefield(EffectResult.instance(choose), "to the battlefield.");
			SetGenerator thatCreature = NewObjectOf.instance(EffectResult.instance(raise));

			ContinuousEffect.Part abilities = addAbilityToObject(thatCreature, org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.Trample.class, org.rnd.jmagic.abilities.keywords.Haste.class);
			EventFactory grantAbilities = createFloatingEffect("It gains flying, trample, and haste.", abilities);
			grantAbilities.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));

			EventFactory exile = exile(delayedTriggerContext(thatCreature), "Exile that card.");

			EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile that card at the beginning of your next end step.");
			exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfYourEndStep()));
			exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));

			SetPattern anywhereElse = new org.rnd.jmagic.abilities.keywords.Flashback.FlashbackExileReplacement.NotTheExileZonePattern();

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(game, "If it would leave the battlefield, exile it instead of putting it anywhere else.");
			replacement.addPattern(new SimpleZoneChangePattern(new SimpleSetPattern(Battlefield.instance()), anywhereElse, new SimpleSetPattern(thatCreature), true));
			replacement.changeDestination(ExileZone.instance());
			EventFactory exileInstead = createFloatingReplacement(replacement, "If it would leave the battlefield, exile it instead of putting it anywhere else.");

			EventFactory itsALotLikeUnearth = sequence(choose, raise, grantAbilities, exileLater, exileInstead);
			this.addEffect(ifThen(mayPay, itsALotLikeUnearth, "You may pay (2)(B). If you do, return a creature card at random from your graveyard to the battlefield. It gains flying, trample, and haste. Exile that card at the beginning of your next end step. If it would leave the battlefield, exile it instead of putting it anywhere else."));
		}
	}

	public KheruLichLord(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of your upkeep, you may pay (2)(B). If you do,
		// return a creature card at random from your graveyard to the
		// battlefield. It gains flying, trample, and haste. Exile that card at
		// the beginning of your next end step. If it would leave the
		// battlefield, exile it instead of putting it anywhere else.
		this.addAbility(new KheruLichLordAbility0(state));
	}
}
