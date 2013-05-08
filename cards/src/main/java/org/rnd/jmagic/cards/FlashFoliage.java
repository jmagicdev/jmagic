package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Flash Foliage")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class FlashFoliage extends Card
{
	public static final class FlashFoliageAbility0 extends StaticAbility
	{
		public FlashFoliageAbility0(GameState state)
		{
			super(state, "Cast Flash Foliage only during combat after blockers are declared.");

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, You.instance());
			castSpell.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part prohibitEffect = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibitEffect.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(prohibitEffect);

			SetGenerator players = Players.instance();
			SetGenerator acceptableSteps = Union.instance(DeclareBlockersStepOf.instance(players), CombatDamageStepOf.instance(players), EndOfCombatStepOf.instance(players));
			SetGenerator blockersHaventBeenDeclared = Not.instance(Intersect.instance(CurrentStep.instance(), acceptableSteps));
			this.canApply = blockersHaventBeenDeclared;
		}
	}

	public FlashFoliage(GameState state)
	{
		super(state);

		// Cast Flash Foliage only during combat after blockers are declared.
		this.addAbility(new FlashFoliageAbility0(state));

		// Target creature attacking me
		SetGenerator creatures = HasType.instance(Type.CREATURE);
		SetGenerator attackingMe = Attacking.instance(You.instance());
		Target target = this.addTarget(Intersect.instance(attackingMe, creatures), "target creature attacking you");

		// Put a 1/1 green Saproling creature token onto the battlefield
		// blocking target creature attacking you
		EventFactory token = new EventFactory(EventType.CREATE_TOKEN_BLOCKING, "Put a 1/1 green Saproling creature token onto the battlefield blocking target creature attacking you.");
		token.parameters.put(EventType.Parameter.CAUSE, This.instance());
		token.parameters.put(EventType.Parameter.ATTACKER, targetedBy(target));
		token.parameters.put(EventType.Parameter.COLOR, Identity.instance(Color.GREEN));
		token.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		token.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		token.parameters.put(EventType.Parameter.POWER, numberGenerator(1));
		token.parameters.put(EventType.Parameter.SUBTYPE, Identity.instance((Object)(java.util.Arrays.asList(SubType.SAPROLING))));
		token.parameters.put(EventType.Parameter.TYPE, Identity.instance(Type.CREATURE));
		token.parameters.put(EventType.Parameter.TOUGHNESS, numberGenerator(1));
		this.addEffect(token);

		// Draw a card
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
