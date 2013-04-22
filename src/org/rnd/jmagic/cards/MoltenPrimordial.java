package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Molten Primordial")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("5RR")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MoltenPrimordial extends Card
{
	public static final class MoltenPrimordialAbility1 extends EventTriggeredAbility
	{
		public MoltenPrimordialAbility1(GameState state)
		{
			super(state, "When Molten Primordial enters the battlefield, for each opponent, gain control of up to one target creature that player controls until end of turn. Untap those creatures. They gain haste until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator legalTargets = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			Target t = new SylvanPrimordial.SylvanTarget(legalTargets, "up to one target creature each opponent controls");
			this.addTarget(t);
			SetGenerator target = targetedBy(t);

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("For each opponent, gain control of up to one target creature that player controls until end of turn.", controlPart));

			this.addEffect(untap(target, "Untap those creatures."));

			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Haste.class, "They gain haste until end of turn."));
		}
	}

	public MoltenPrimordial(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Molten Primordial enters the battlefield, for each opponent,
		// gain control of up to one target creature that player controls until
		// end of turn. Untap those creatures. They gain haste until end of
		// turn.
		this.addAbility(new MoltenPrimordialAbility1(state));
	}
}
