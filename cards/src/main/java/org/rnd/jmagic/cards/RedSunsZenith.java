package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Red Sun's Zenith")
@Types({Type.SORCERY})
@ManaCost("XR")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class RedSunsZenith extends Card
{
	public RedSunsZenith(GameState state)
	{
		super(state);

		// Red Sun's Zenith deals X damage to target creature or player. If a
		// creature dealt damage this way would be put into a graveyard this
		// turn, exile it instead. Shuffle Red Sun's Zenith into its owner's
		// library.

		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "creature or player"));

		EventFactory dealDamage = spellDealDamage(ValueOfX.instance(This.instance()), target, "Red Sun's Zenith deals X damage to target creature or player.");
		this.addEffect(dealDamage);

		state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
		SetGenerator damaged = DealtDamageByThisTurn.instance(This.instance());
		SetGenerator damagedCreatures = Intersect.instance(CreaturePermanents.instance(), damaged);

		ZoneChangeReplacementEffect exileInstead = new ZoneChangeReplacementEffect(state.game, "If a creature dealt damage this way would be put into a graveyard this turn, exile it instead");
		exileInstead.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), damagedCreatures, true));
		exileInstead.changeDestination(ExileZone.instance());

		SetGenerator untilEndOfTurn = Intersect.instance(CurrentStep.instance(), CleanupStepOf.instance(Players.instance()));

		EventFactory replacement = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "If a creature dealt damage this way would be put into a graveyard this turn, exile it instead.");
		replacement.parameters.put(EventType.Parameter.CAUSE, This.instance());
		replacement.parameters.put(EventType.Parameter.EFFECT, Identity.instance(replacementEffectPart(exileInstead)));
		replacement.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(untilEndOfTurn));
		this.addEffect(replacement);

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle Red Sun's Zenith into its owner's library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), OwnerOf.instance(This.instance())));
		this.addEffect(shuffle);
	}
}
