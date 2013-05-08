package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angel's Grace")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AngelsGrace extends Card
{
	public AngelsGrace(GameState state)
	{
		super(state);

		// Split second
		this.addAbility(new org.rnd.jmagic.abilities.keywords.SplitSecond(state));

		// You can't lose the game this turn and your opponents can't win the
		// game this turn. Until end of turn, damage that would reduce your life
		// total to less than 1 reduces it to 1 instead.
		SimpleEventPattern loseEvent = new SimpleEventPattern(EventType.LOSE_GAME);
		loseEvent.put(EventType.Parameter.PLAYER, You.instance());

		SimpleEventPattern winEvent = new SimpleEventPattern(EventType.WIN_GAME);
		winEvent.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));

		ContinuousEffect.Part cantLose = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		cantLose.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(winEvent, loseEvent));

		ContinuousEffect.Part cantDie = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_REDUCE_LIFE_TOTAL_TO_LESS_THAN);
		cantDie.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		cantDie.parameters.put(ContinuousEffectType.Parameter.NUMBER, numberGenerator(1));

		this.addEffect(createFloatingEffect("You can't lose the game this turn and your opponents can't win the game this turn. Until end of turn, damage that would reduce your life total to less than 1 reduces it to 1 instead.", cantDie, cantDie));
	}
}
