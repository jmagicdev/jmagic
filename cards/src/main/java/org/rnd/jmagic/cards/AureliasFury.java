package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aurelia's Fury")
@Types({Type.INSTANT})
@ManaCost("XRW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class AureliasFury extends Card
{
	public AureliasFury(GameState state)
	{
		super(state);

		state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());

		SetGenerator damaged = DealtDamageByThisTurn.instance(This.instance());
		SetGenerator damagedCreatures = Intersect.instance(CreaturePermanents.instance(), damaged);
		SetGenerator damagedPlayers = Intersect.instance(Players.instance(), damaged);

		// Aurelia's Fury deals X damage divided as you choose among any number
		// of target creatures and/or players. Tap each creature dealt damage
		// this way. Players dealt damage this way can't cast noncreature spells
		// this turn.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "any number of target creatures and/or players");
		target.setNumber(1, null);
		this.setDivision(Union.instance(ValueOfX.instance(This.instance()), Identity.instance("damage")));

		EventFactory damage = new EventFactory(EventType.DISTRIBUTE_DAMAGE, "Aurelia's Fury deals X damage divided as you choose among any number of target creatures and/or players.");
		damage.parameters.put(EventType.Parameter.SOURCE, This.instance());
		damage.parameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(damage);

		this.addEffect(tap(damagedCreatures, "Tap each creature dealt damage this way."));

		PlayProhibition castSomething = new PlayProhibition(damagedPlayers, c -> !c.types.contains(Type.CREATURE));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSomething));
		this.addEffect(createFloatingEffect("Players dealt damage this way can't cast noncreature spells this turn.", part));
	}
}
