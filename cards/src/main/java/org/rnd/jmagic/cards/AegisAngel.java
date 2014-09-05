package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Aegis Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AegisAngel extends Card
{
	public static final class AegisAngelAbility1 extends EventTriggeredAbility
	{
		public AegisAngelAbility1(GameState state)
		{
			super(state, "When Aegis Angel enters the battlefield, another target permanent has indestructible for as long as you control Aegis Angel.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator legal = RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(legal, "another target permanent"));

			SetGenerator youControlThis = Intersect.instance(ABILITY_SOURCE_OF_THIS, ControlledBy.instance(You.instance()));

			ContinuousEffect.Part indestructible = addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Indestructible.class);
			EventFactory effect = createFloatingEffect("Another target permanent has indestructible for as long as you control Aegis Angel.", indestructible);
			effect.parameters.put(EventType.Parameter.EXPIRES, Not.instance(youControlThis));
			this.addEffect(effect);
		}
	}

	public AegisAngel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Aegis Angel enters the battlefield, another target permanent is
		// indestructible for as long as you control Aegis Angel. (Effects that
		// say "destroy" don't destroy that permanent. An indestructible
		// creature can't be destroyed by damage.)
		this.addAbility(new AegisAngelAbility1(state));
	}
}
